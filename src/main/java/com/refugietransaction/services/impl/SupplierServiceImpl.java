package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.dto.SupplierListDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.Admin;
import com.refugietransaction.model.Supplier;
import com.refugietransaction.repository.AdminRepository;
import com.refugietransaction.repository.MagasinierRepository;
import com.refugietransaction.repository.SupplierRepository;
import com.refugietransaction.services.SupplierService;
import com.refugietransaction.validator.SupplierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {
	
	private SupplierRepository supplierRepository;
	private AdminRepository adminRepository;
	private MagasinierRepository magasinierRepository;
	
	@Autowired
	public SupplierServiceImpl(SupplierRepository supplierRepository, AdminRepository adminRepository, MagasinierRepository magasinierRepository) {
		this.supplierRepository = supplierRepository;
		this.adminRepository = adminRepository;
		this.magasinierRepository = magasinierRepository;
	}
	
	@Override
	public SupplierDto save(SupplierDto dto) {
		List<String> errors = SupplierValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Supplier is not valid {}", dto);
			throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.SUPPLIER_NOT_VALID, errors);
		}
		
		if((dto.getId() == null || dto.getId().compareTo(0L) == 0)) {
			
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
		
		Supplier existingSupplier = supplierRepository.findSupplierById(dto.getId());
		if(existingSupplier != null && !existingSupplier.getName().equals(dto.getName())) {
			
			if(supplierAlreadyExists(dto.getName())) {
				throw new InvalidEntityException("Un autre fournisseur avec le meme nom existe deja", ErrorCodes.SUPPLIER_ALREADY_EXISTS, 
						Collections.singletonList("Un autre fournisseur avec le meme nom existe deja dans la BDD"));
			}
		}
		
		if(existingSupplier != null && !existingSupplier.getPhoneNumber().equals(dto.getPhoneNumber())) {
			
			if(phoneNumberAlreadyExists(dto.getPhoneNumber())) {
				throw new InvalidEntityException("Un autre fournisseur avec le meme numero de telephone existe deja", ErrorCodes.SUPPLIER_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Un autre fournisseur avec le meme numero de telephone existe deja dans la BDD"));
			}
		}
		
		return SupplierDto.fromEntity(
				supplierRepository.save(SupplierDto.toEntity(dto))
		);
		
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
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Supplier ID is null");
		}
		
		List<Admin> admin = adminRepository.findAllBySupplierId(id);
		if(!admin.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer ce fournisseur qui est deja utilisé", 
					ErrorCodes.SUPPLIER_ALREADY_IN_USE);
		}
		
		supplierRepository.deleteById(id);
		
	}

	@Override
	public Page<SupplierListDto> findByNamePhoneAddressLike(String search, Pageable pageable) {
		Page<Supplier> suppliers;
		if(search != null) {
			suppliers = supplierRepository.findByNamePhoneAddressLike(search, pageable);
		} else {
			suppliers = supplierRepository.findAllSuppliers(pageable);
		}
		
		return suppliers.map(SupplierListDto::fromEntity);
	}

	@Override
	@Transactional
	public void enableSupplier(Long supplierId) {
		
		Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
		if(optionalSupplier.isPresent()) {
			Supplier supplier = optionalSupplier.get();
			supplier.setIsSupplierActive(true);
			supplierRepository.save(supplier);
		} else {
			throw new EntityNotFoundException("Aucun fournisseur avec l'ID = " +supplierId+ "n'a ete trouve dans la BDD", 
					ErrorCodes.SUPPLIER_NOT_FOUND);
		}
		
	}

	@Override
	@Transactional
	public void desableSupplier(Long supplierId) {
		
		Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
		if(optionalSupplier.isPresent()) {
			Supplier supplier = optionalSupplier.get();
			supplier.setIsSupplierActive(false);
			supplierRepository.save(supplier);
		} else {
			throw new EntityNotFoundException("Aucun fournisseur avec l'ID = " +supplierId+ "n'a ete trouve dans la BDD", 
					ErrorCodes.SUPPLIER_NOT_FOUND);
		}
		
	}

	@Override
	public List<SupplierDto> findSuppliersWithNoMainAdmin() {
		
		return supplierRepository.findSuppliersWithNoMainAdmin().stream()
				.map(SupplierDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<SupplierDto> findAll() {
		
		return supplierRepository.findAll().stream()
				.map(SupplierDto::fromEntity)
				.collect(Collectors.toList());
	}

}
