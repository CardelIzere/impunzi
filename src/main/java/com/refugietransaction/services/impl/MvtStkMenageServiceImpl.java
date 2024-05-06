package com.refugietransaction.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.ProductTypeDistributionDto;
import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.Camp;
import com.refugietransaction.model.Menage;
import com.refugietransaction.model.MvtStkMenage;
import com.refugietransaction.model.TypeMvtStkMenageEnum;
import com.refugietransaction.repository.CampRepository;
import com.refugietransaction.repository.MenageRepository;
import com.refugietransaction.repository.MvtStkMenageRepository;
import com.refugietransaction.services.MvtStkMenageService;
import com.refugietransaction.validator.ProductTypeDistributionValidator;
import com.refugietransaction.validator.ProductTypeDistributionValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MvtStkMenageServiceImpl implements MvtStkMenageService {
	
	private final MvtStkMenageRepository mvtStkMenageRepository;
	private final MenageRepository menageRepository;
	private final CampRepository campRepository;
	
	@Autowired
	public MvtStkMenageServiceImpl(MvtStkMenageRepository mvtStkMenageRepository, MenageRepository menageRepository, CampRepository campRepository) {
		this.mvtStkMenageRepository = mvtStkMenageRepository;
		this.menageRepository = menageRepository;
		this.campRepository = campRepository;
	}
	
	@Override
	public void productTypeDistribution(ProductTypeDistributionDto productTypeDistributionDto) {
		
		List<String> errors = ProductTypeDistributionValidator.validate(productTypeDistributionDto);
        if (!errors.isEmpty()) {
            log.error("Distribution is not valid {}", productTypeDistributionDto);
            throw new InvalidEntityException("La distribution  n'est pas valide", ErrorCodes.PRODUCT_TYPE_DISTRIBUTION_NOT_VALID, errors);
        }

        Optional<Camp> camp=campRepository.findById(productTypeDistributionDto.getCampDto().getId());
        if (camp.isPresent()){

            List<Menage> menages=menageRepository.findAllByCamp_Id(productTypeDistributionDto.getCampDto().getId());
            menages.forEach(menage -> {
                Integer nbPersonnes=menage.getNombrePersonnes();
                BigDecimal consParPersonne=productTypeDistributionDto.getCons_moyenne_personne();
                BigDecimal quantity=BigDecimal.valueOf(nbPersonnes).multiply(consParPersonne);

                MvtStkMenage mvtStkMenage=new MvtStkMenage();
                mvtStkMenage.setDateMvt(Instant.now());
                mvtStkMenage.setQuantite(quantity);
                mvtStkMenage.setProductType(ProductTypeDto.toEntity(productTypeDistributionDto.getProductTypeDto()));
                mvtStkMenage.setTypeMvtStkMenageEnum(TypeMvtStkMenageEnum.RECEPTION);
                mvtStkMenage.setMenage(menage);
                mvtStkMenageRepository.save(mvtStkMenage);


            });

        }
        else {
            throw new EntityNotFoundException(
                    "Aucun camp avec l'ID = " + productTypeDistributionDto.getCampDto().getId() + " n'a ete trouvé dans la BDD",
                    ErrorCodes.CAMP_NOT_FOUND);
        }

		
	}

	@Override
	public BigDecimal stockReelMenage(Long idProductType, Long idMenage) {
		
		if(idProductType == null) {
			log.warn("ID Product Type is null");
			return BigDecimal.valueOf(-1);
		}
		
		if(idMenage == null) {
			log.warn("ID Menage is null");
			return BigDecimal.valueOf(-1);
		}
		
		return mvtStkMenageRepository.stockReelMenage(idProductType, idMenage);
	}

	@Override
	public List<MvtStkMenageDto> mvtStkProductTypeMenage(Long idProductType, Long idMenage) {
		
		return mvtStkMenageRepository.findAllByProductTypeIdAndMenageId(idProductType, idMenage).stream()
				.map(MvtStkMenageDto::fromEntity)
				.collect(Collectors.toList());
	}

}
