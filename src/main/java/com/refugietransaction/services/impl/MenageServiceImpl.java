package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.CampDto;
import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.Menage;
import com.refugietransaction.model.MouvementStock;
import com.refugietransaction.repository.MenageRepository;
import com.refugietransaction.repository.MouvementStockRepository;
import com.refugietransaction.services.MenageService;
import com.refugietransaction.validator.CampValidator;
import com.refugietransaction.validator.MenageValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenageServiceImpl implements MenageService {
	
	private MenageRepository menageRepository;
	private MouvementStockRepository mouvementStockRepository;
	
	@Autowired
	public MenageServiceImpl(MenageRepository menageRepository, MouvementStockRepository mouvementStockRepository) {
		this.menageRepository = menageRepository;
		this.mouvementStockRepository = mouvementStockRepository;
	}
	
	
	@Override
	public MenageDto save(MenageDto dto) {
		
		List<String> errors = MenageValidator.validate(dto);
	    if (!errors.isEmpty()) {
	      log.error("Mouvement Stock is not valid {}", dto);
	      throw new InvalidEntityException("La menage n'est pas valide", ErrorCodes.MENAGE_NOT_VALID, errors);
	    }
	    
	    if(menageAlreadyExists(dto.getIdNumber())) {
	    	throw new InvalidEntityException("Un autre menage avec le meme numero existe deja", ErrorCodes.MENAGE_ALREADY_EXISTS,
	    			Collections.singletonList("Un autre menage avec le meme numero existe deja dans la BDD"));
	    }
	    
	    if(phoneNumberAlreadyExists(dto.getNumTelephone())) {
	    	throw new InvalidEntityException("Une autre personne de contact avec le meme numero de telephone existe deja", ErrorCodes.MENAGE_PHONE_NUMBER_ALREADY_EXISTS,
	    			Collections.singletonList("Une autre personne de contact avec le meme numero de telephone existe dans la BDD"));
	    }
	    
	    if(dto.getId()==null) {
	    	dto.setIdNumber(randomNumber());
	    }
	    else {
	    	Long m=menageRepository.findMenageById(dto.getId()).getIdNumber();
	    	dto.setIdNumber(m);
	    }
	    
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
	public List<MenageDto> findAll() {
		
		return menageRepository.findAll().stream()
				.map(MenageDto::fromEntity)
				.collect(Collectors.toList());
	}


	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Menage ID is null");
		}
		List<MouvementStock> mouvementStocks = mouvementStockRepository.findAllById(id);
		if(!mouvementStocks.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer un menage ayant au moins un mouvement stock",
					ErrorCodes.MENAGE_ALREADY_IN_USE);
		}
		menageRepository.deleteById(id);
		
	}
	
}
