package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.SalesUnitDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.model.SalesUnit;
import com.refugietransaction.repository.ProductTypeRepository;
import com.refugietransaction.repository.SalesUnitRepository;
import com.refugietransaction.services.SalesUnitService;
import com.refugietransaction.validator.SalesUnitValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesUnitServiceImpl implements SalesUnitService {
	
	private final SalesUnitRepository salesUnitRepository;
	private final ProductTypeRepository productTypeRepository;
	
	@Autowired
	public SalesUnitServiceImpl(SalesUnitRepository salesUnitRepository, ProductTypeRepository productTypeRepository) {
		this.salesUnitRepository = salesUnitRepository;
		this.productTypeRepository = productTypeRepository;
	}
	
	@Override
	public SalesUnitDto save(SalesUnitDto dto) {
		List<String> errors = SalesUnitValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Sales Unit is not valid {}", dto);
			throw new InvalidEntityException("L'unité de vente n'est pas valide", ErrorCodes.SALESUNIT_NOT_VALID, errors);
		}
		
		if((dto.getId() == null || dto.getId().compareTo(0L) == 0)) {
			
			if(salesUnitAlreadyExists(dto.getName())) {
				throw new InvalidEntityException("Une autre unité de vente avec le meme nom existe deja", ErrorCodes.SALESUNIT_ALREADY_EXISTS, 
						Collections.singletonList("Un autre unité de vente avec le meme nom existe deja dans la BDD"));
			}
			
			return SalesUnitDto.fromEntity(
					salesUnitRepository.save(SalesUnitDto.toEntity(dto))
			);
		}
		
		SalesUnit existingSalesUnit = salesUnitRepository.findSalesUnitById(dto.getId());
		if(existingSalesUnit != null && !existingSalesUnit.getName().equals(dto.getName())) {
			
			if(salesUnitAlreadyExists(dto.getName())) {
				throw new InvalidEntityException("Un autre unité de vente avec le meme nom existe deja", ErrorCodes.SALESUNIT_ALREADY_EXISTS, 
						Collections.singletonList("Un autre unité de vente avec le meme nom existe deja dans la BDD"));
			}
		}
		
		return SalesUnitDto.fromEntity(
				salesUnitRepository.save(SalesUnitDto.toEntity(dto))
		);
	}

	private boolean salesUnitAlreadyExists(String name) {
		Optional<SalesUnit> salesUnit = salesUnitRepository.findSalesUnitByName(name);
		return salesUnit.isPresent();
	}

	@Override
	public SalesUnitDto findById(Long id) {
		
		if(id == null) {
			log.error("Sales Unit ID is null");
			return null;
		}
		
		return salesUnitRepository.findById(id)
				.map(SalesUnitDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucune unité de vente avec l'ID = " +id+ "n'a ete trouve dans la BDD", 
						ErrorCodes.SALESUNIT_NOT_FOUND));
	}

	@Override
	public List<SalesUnitDto> findAll() {
		
		return salesUnitRepository.findAll().stream()
				.map(SalesUnitDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<SalesUnitDto> findByNameLike(String search, Pageable pageable) {
		Page<SalesUnit> salesUnits;
		if(search != null) {
			salesUnits = salesUnitRepository.findByNameLike(search, pageable);
		} else {
			salesUnits = salesUnitRepository.findAllSalesUnits(pageable);
		}
		
		return salesUnits.map(SalesUnitDto::fromEntity);
	}

	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Sales Unit ID is null");
		}
		
		salesUnitRepository.deleteById(id);
		
	}

}
