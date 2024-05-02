package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.Supplier;
import com.refugietransaction.repository.SupplierRepository;
import com.refugietransaction.services.SupplierService;
import com.refugietransaction.validator.SupplierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {
	
	private SupplierRepository supplierRepository;
	
	@Autowired
	public SupplierServiceImpl(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}
	
	@Override
	public SupplierDto save(SupplierDto dto) {
		List<String> errors = SupplierValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Supplier is not valid {}", dto);
			throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.SUPPLIER_NOT_VALID, errors);
		}
		
		if(supplierAlreadyExists(dto.getName())) {
			throw new InvalidEntityException("Un autre fournisseur avec le meme nom existe deja", ErrorCodes.SUPPLIER_ALREADY_EXISTS,
					Collections.singletonList("Un autre fournisseur avec le meme nom existe deja dans la BDD"));
		}
		
		if(phoneNumberAlreadyExists(dto.getPhoneNumber())) {
			throw new InvalidEntityException("Un autre fournisseur avec le meme numero de telephone existe deja", ErrorCodes.SUPPLIER_PHONE_NUMBER_ALREADY_EXISTS,
					Collections.singletonList("Un autre fournisseur avec le meme numero de telephone existe deja dans la BDD"));
		}
		
		return SupplierDto.fromEntity(
				supplierRepository.save(SupplierDto.toEntity(dto)));
	}

	private boolean phoneNumberAlreadyExists(String phoneNumber) {
		Optional<Supplier> supplier = supplierRepository.findSupplierByPhoneNumber(phoneNumber);
		return supplier.isPresent();
	}

	private boolean supplierAlreadyExists(String name) {
		Optional<Supplier> supplier = supplierRepository.findSupplierByName(name);
		return supplier.isPresent();
	}

	@Override
	public SupplierDto findById(Long id) {
		if(id == null) {
			log.error("Supplier ID is null");
			return null;
		}
		return supplierRepository.findById(id)
				.map(SupplierDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun fournisseur avec l'ID = " +id+ " n'a ete trouve dans la BDD", 
						ErrorCodes.SUPPLIER_NOT_FOUND)
						);
	}

	@Override
	public List<SupplierDto> findAll() {
		
		return supplierRepository.findAll().stream()
				.map(SupplierDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Supplier ID is null");
		}
		
		supplierRepository.deleteById(id);
		
	}

}
