package com.refugietransaction.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.MenageStockDto;
import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.ProductTypeDistributionDto;
import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.dto.SalesUnitDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.exceptions.InvalidOperationException;
import com.refugietransaction.model.Camp;
import com.refugietransaction.model.Menage;
import com.refugietransaction.model.MvtStkMenage;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.model.TypeDistributionEnum;
import com.refugietransaction.model.TypeMvtStkMenageEnum;
import com.refugietransaction.repository.CampRepository;
import com.refugietransaction.repository.MenageRepository;
import com.refugietransaction.repository.MvtStkMenageRepository;
import com.refugietransaction.repository.ProductTypeRepository;
import com.refugietransaction.services.MvtStkMenageService;
import com.refugietransaction.validator.MvtStkMenageValidator;
import com.refugietransaction.validator.ProductTypeDistributionValidator;
import com.refugietransaction.validator.ProductTypeDistributionValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MvtStkMenageServiceImpl implements MvtStkMenageService {
	
	private final MvtStkMenageRepository mvtStkMenageRepository;
	private final MenageRepository menageRepository;
	private final CampRepository campRepository;
	private final ProductTypeRepository productTypeRepository;
	
	@Autowired
	public MvtStkMenageServiceImpl(MvtStkMenageRepository mvtStkMenageRepository, MenageRepository menageRepository, CampRepository campRepository, ProductTypeRepository productTypeRepository) {
		this.mvtStkMenageRepository = mvtStkMenageRepository;
		this.menageRepository = menageRepository;
		this.campRepository = campRepository;
		this.productTypeRepository = productTypeRepository;
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
                
                Optional<MvtStkMenage> lastMvtStkMenage = mvtStkMenageRepository.findMenageLastDistribution(menage.getId());
                
                if(!lastMvtStkMenage.isPresent() || lastMvtStkMenage.get().getDateNextDistribution().isBefore(LocalDate.now())) {
                	
                	MvtStkMenage mvtStkMenage=new MvtStkMenage();
	                mvtStkMenage.setDateMvt(LocalDate.now());
	                mvtStkMenage.setQuantite(quantity);
	                mvtStkMenage.setProductType(ProductTypeDto.toEntity(productTypeDistributionDto.getProductTypeDto()));
	                mvtStkMenage.setTypeMvtStkMenageEnum(TypeMvtStkMenageEnum.RECEPTION);
	                mvtStkMenage.setMenage(menage);
	                
	                //Set dateNextDistribution based on typeDistribution
	                switch(productTypeDistributionDto.getTypeDistributionEnum()) {
		                case DAILY:
		                	mvtStkMenage.setDateNextDistribution(LocalDate.now().plusDays(1));
		                	mvtStkMenage.setTypeDistributionEnum(TypeDistributionEnum.DAILY);
		                	break;
		                case WEEKLY:
		                	mvtStkMenage.setDateNextDistribution(LocalDate.now().plusDays(7));
		                	mvtStkMenage.setTypeDistributionEnum(TypeDistributionEnum.WEEKLY);
		                	break;
		                case MONTHLY:
		                	mvtStkMenage.setDateNextDistribution(LocalDate.now().plusMonths(1));
		                	mvtStkMenage.setTypeDistributionEnum(TypeDistributionEnum.MONTHLY);
		                	break;
		                case QUARTERLY:
		                	mvtStkMenage.setDateNextDistribution(LocalDate.now().plusMonths(3));
		                	mvtStkMenage.setTypeDistributionEnum(TypeDistributionEnum.QUARTERLY);
		                	break;
		                case SEMI_ANNUALY:
		                	mvtStkMenage.setDateNextDistribution(LocalDate.now().plusMonths(6));
		                	mvtStkMenage.setTypeDistributionEnum(TypeDistributionEnum.SEMI_ANNUALY);
		                	break;
	                }
	                
	                mvtStkMenageRepository.save(mvtStkMenage);
                } else {
//                	
                	throw new EntityNotFoundException("La distribution est impossible car la date suivante de distribution n'est pas encore atteinte");
                }
                
               


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

	@Override
	public Page<MvtStkMenageDto> findEntriesByProductTypeMenageLike(String search, Pageable pageable) {
		Page<MvtStkMenage> mvtStkMenages;
		if(search != null) {
			mvtStkMenages = mvtStkMenageRepository.findEntriesByProductTypeMenageLike(search, pageable);
		} else {
			mvtStkMenages = mvtStkMenageRepository.findAllEntries(pageable);
		}
		
		return mvtStkMenages.map(MvtStkMenageDto::fromEntity);
	}

	@Override
	public MvtStkMenageDto sortie(MvtStkMenageDto dto) {
		List<String> errors = MvtStkMenageValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("HouseHold Stock mouvement is not valid {}", dto);
			throw new InvalidEntityException("Le mouvement stock du menage n'est pas valide", ErrorCodes.MVTSTK_MENAGE_NOT_VALID, errors);
		}
		
		dto.setDateMvt(LocalDate.now());
		dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue()) * -1));
		dto.setTypeMvtStkMenage(TypeMvtStkMenageEnum.ACHAT);
		
		return MvtStkMenageDto.fromEntity(
				mvtStkMenageRepository.save(MvtStkMenageDto.toEntity(dto))
		);
	}

	@Override
	public List<MvtStkMenageDto> findMvtStkMenageByMenageId(Long idMenage) {
		
		return mvtStkMenageRepository.findMvtStkMenageByMenageId(idMenage).stream()
				.map(MvtStkMenageDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<MvtStkMenageDto> findAllByProductTypeMenageLike(String search, Pageable pageable) {
		Page<MvtStkMenage> mvtStkMenages;
		if(search != null) {
			mvtStkMenages = mvtStkMenageRepository.findAllByProductTypeMenageLike(search, pageable);
		} else {
			mvtStkMenages = mvtStkMenageRepository.findAllMvtStkMenages(pageable);
		}
		return mvtStkMenages.map(MvtStkMenageDto::fromEntity);
	}

	@Override
	public List<MenageStockDto> getTotalQuantityByIdMenage(Long idMenage) {
		List<Object[]> results = mvtStkMenageRepository.findTotalQuantityByIdMenage(idMenage);
		
		return results.stream().map(result->{
			ProductType productType = (ProductType) result[0];
			BigDecimal totalQuantity = (BigDecimal) result[1];
			
			ProductTypeDto productTypeDto = ProductTypeDto.fromEntity(productType);
			
			MenageStockDto menageStockDto = new MenageStockDto();
			
			menageStockDto.setProductType(productTypeDto);
			menageStockDto.setInStockQuantity(totalQuantity);
			
			return menageStockDto;
			
		}).collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Mvt Stk Menage ID is null");
		}
		
		List<ProductType> productTypes = productTypeRepository.findAllById(id);
		if(!productTypes.isEmpty()) {
			throw new InvalidOperationException("Impossible de supprimer ce mouvement stock qui est deja utilisé", 
					ErrorCodes.MVTSTK_MENAGE_ALREADY_IN_USE);
		}
		
		List<Menage> menages = menageRepository.findAllById(id);
		if(!menages.isEmpty()) {
			throw new InvalidOperationException("Impossible de supprimer ce mouvement stock qui est deja utilisé", 
					ErrorCodes.MVTSTK_MENAGE_ALREADY_IN_USE);
		}
		
		mvtStkMenageRepository.deleteById(id);
		
	}


}
