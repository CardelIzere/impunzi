package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.CampDto;
import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.Camp;
import com.refugietransaction.model.Menage;
import com.refugietransaction.model.MvtStkMenage;
import com.refugietransaction.model.MvtStkSupplier;
import com.refugietransaction.repository.CampRepository;
import com.refugietransaction.repository.MenageRepository;
import com.refugietransaction.repository.MvtStkMenageRepository;
import com.refugietransaction.repository.MvtStkSupplierRepository;
import com.refugietransaction.services.MenageService;
import com.refugietransaction.validator.CampValidator;
import com.refugietransaction.validator.MenageValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenageServiceImpl implements MenageService {
	
	private MenageRepository menageRepository;
	private MvtStkMenageRepository mouvementStockRepository;
	private CampRepository campRepository;
	
	@Autowired
	public MenageServiceImpl(MenageRepository menageRepository, MvtStkMenageRepository mouvementStockRepository, CampRepository campRepository) {
		this.menageRepository = menageRepository;
		this.mouvementStockRepository = mouvementStockRepository;
		this.campRepository = campRepository;
	}
	
	
	@Override
	public MenageDto save(MenageDto dto) {
		
		List<String> errors = MenageValidator.validate(dto);
	    if (!errors.isEmpty()) {
	      log.error("Mouvement Stock is not valid {}", dto);
	      throw new InvalidEntityException("Le menage n'est pas valide", ErrorCodes.MENAGE_NOT_VALID, errors);
	    }
	    
	    if((dto.getId() == null || dto.getId().compareTo(0L) == 0)) {
	    	
	    	if(menageAlreadyExists(dto.getIdNumber())) {
	    	throw new InvalidEntityException("Un autre menage avec le meme numero existe deja", ErrorCodes.MENAGE_ALREADY_EXISTS,
	    			Collections.singletonList("Un autre menage avec le meme numero existe deja dans la BDD"));
		    }
		    
		    if(phoneNumberAlreadyExists(dto.getNumTelephone())) {
		    	throw new InvalidEntityException("Une autre personne de contact avec le meme numero de telephone existe deja", ErrorCodes.MENAGE_PHONE_NUMBER_ALREADY_EXISTS,
		    			Collections.singletonList("Une autre personne de contact avec le meme numero de telephone existe deja dans la BDD"));
		    }
		    
		    dto.setIdNumber(randomNumber());
		    
			return MenageDto.fromEntity(
					menageRepository.save(MenageDto.toEntity(dto))
			);
	    }
	    
	    Menage existingMenage = menageRepository.findMenageById(dto.getId());
	    if(existingMenage != null && !existingMenage.getIdNumber().equals(dto.getIdNumber())) {
	    	
	    	if(menageAlreadyExists(dto.getIdNumber())) {
	    		throw new InvalidEntityException("Une autre menage avec le meme numero existe deja", ErrorCodes.MENAGE_ALREADY_EXISTS, 
	    				Collections.singletonList("Une autre menage avec le meme numero existe deja dans la BDD"));
	    	}
	    }
	    
	    if(existingMenage != null && !existingMenage.getNumTelephone().equals(dto.getNumTelephone())) {
	    	
	    	if(phoneNumberAlreadyExists(dto.getNumTelephone())) {
	    		throw new InvalidEntityException("Une autre personne de contact avec le meme numero de telephone existe deja", ErrorCodes.MENAGE_PHONE_NUMBER_ALREADY_EXISTS, 
	    				Collections.singletonList("Une autre personne de contact avec le meme numero de telephone existe deja dans la BDD"));
	    	}
	    }
	    
	    dto.getIdNumber();
	    
		return MenageDto.fromEntity(
				menageRepository.save(MenageDto.toEntity(dto))
		);
	    
	    
	}
	
	private boolean menageAlreadyExists(Long id_number) {
		Optional<Menage> menage = menageRepository.findMenageByIdNumber(id_number);
		return menage.isPresent();
	}
	
	private boolean phoneNumberAlreadyExists(String phone_number) {
		Optional<Menage> menage = menageRepository.findMenageByPhoneNumber(phone_number);
		return menage.isPresent();
	}
	
	private Optional<Menage> menageIdNumber(Long id){
		return menageRepository.findById(id);
	}
	
	private Long randomNumber() {
		ThreadLocalRandom random=ThreadLocalRandom.current();
		return random.nextLong(100000, 1000000);
	}

	@Override
	public MenageDto findById(Long id) {
		
		 if (id == null) {
		      log.error("Menage ID is null");
		      return null;
		    }
		return menageRepository.findById(id)
				.map(MenageDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucune menage avec l'ID = " +id+ " n' a ete trouve dans la BDD",
						ErrorCodes.MENAGE_NOT_FOUND)
						);
	}


	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Menage ID is null");
		}
		
		List<Camp> camps = campRepository.findAllById(id);
		if(!camps.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer une menage ayant au moins un camp",
					ErrorCodes.MENAGE_ALREADY_IN_USE);
		}
		
		List<MvtStkMenage> mouvementStocks = mouvementStockRepository.findAllById(id);
		if(!mouvementStocks.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer un menage ayant au moins un mouvement stock",
					ErrorCodes.MENAGE_ALREADY_IN_USE);
		}
		menageRepository.deleteById(id);
		
	}


	@Override
	public Page<MenageDto> findByPersonneContactNumTeleLike(String search, Pageable pageable) {
		Page<Menage> menages;
		if(search != null) {
			menages = menageRepository.findByPersonneContactNumTeleLike(search, pageable);
		} else {
			menages = menageRepository.findAllMenages(pageable);
		}
		
		return menages.map(MenageDto::fromEntity);
	}


	@Override
	public List<MenageDto> findAllMenagesByCampId(Long campId) {
		
		return menageRepository.findAllByCamp_Id(campId).stream()
				.map(MenageDto::fromEntity)
				.collect(Collectors.toList());
	}


	@Override
	public List<MenageDto> findAllByCampIdByPersonneContactNumTeleIdNumberLike(Long idCamp, String search) {
		
		return menageRepository.findAllByCampIdByPersonneContactNumTeleIdNumberLike(idCamp, search).stream()
				.map(MenageDto::fromEntity)
				.collect(Collectors.toList());
	}
	
}
