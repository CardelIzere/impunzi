package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.MvtStkMenage;
import com.refugietransaction.model.Product;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.model.SalesUnit;
import com.refugietransaction.repository.MvtStkMenageRepository;
import com.refugietransaction.repository.ProductRepository;
import com.refugietransaction.repository.ProductTypeRepository;
import com.refugietransaction.repository.SalesUnitRepository;
import com.refugietransaction.services.ProductTypeService;
import com.refugietransaction.validator.ProductTypeValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductTypeServiceImpl implements ProductTypeService {
	
	private final ProductTypeRepository productTypeRepository;
	private final ProductRepository productRepository;
	private final MvtStkMenageRepository mvtStkMenageRepository;
	private final SalesUnitRepository salesUnitRepository;
	
	@Autowired
	public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository, ProductRepository productRepository, MvtStkMenageRepository mvtStkMenageRepository, SalesUnitRepository salesUnitRepository) {
		this.productTypeRepository = productTypeRepository;
		this.productRepository = productRepository;
		this.mvtStkMenageRepository = mvtStkMenageRepository;
		this.salesUnitRepository = salesUnitRepository;
	}

	@Override
	public ProductTypeDto save(ProductTypeDto dto) {
		List<String> errors = ProductTypeValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Product Type is not valid {}", dto);
			throw new InvalidEntityException("Le type de produit n'est pas valide", ErrorCodes.PRODUCTTYPE_NOT_VALID, errors);
		}
		
		if((dto.getId() == null || dto.getId().compareTo(0L) == 0)) {
			
			if(productTypeAlreadyExists(dto.getName())) {
				throw new InvalidEntityException("Un autre type de produit avec le meme nom existe deja", ErrorCodes.PRODUCTTYPE_ALREADY_EXISTS, 
						Collections.singletonList("Un autre type de produit avec le meme nom existe deja dans la BDD"));
			}
			
			return ProductTypeDto.fromEntity(
					productTypeRepository.save(ProductTypeDto.toEntity(dto))
			);
		}
		
		ProductType existingProductType = productTypeRepository.findProductTypeById(dto.getId());
		if(existingProductType != null && !existingProductType.getName().equals(dto.getName())) {
			
			if(productTypeAlreadyExists(dto.getName())) {
				throw new InvalidEntityException("Un autre type de produit avec le meme nom existe deja", ErrorCodes.PRODUCTTYPE_ALREADY_EXISTS, 
						Collections.singletonList("Un autre type de produit avec le meme nom existe deja dans la BDD"));
			}
		}
		
		return ProductTypeDto.fromEntity(
				productTypeRepository.save(ProductTypeDto.toEntity(dto))
		);
	}

	private boolean productTypeAlreadyExists(String name) {
		Optional<ProductType> productType = productTypeRepository.findProductTypeByName(name);
		return productType.isPresent();
	}

	@Override
	public ProductTypeDto findById(Long id) {
		
		if(id == null) {
			log.error("Product Type is null");
			return null;
		}
		return productTypeRepository.findById(id)
				.map(ProductTypeDto::fromEntity)
				.orElseThrow(()-> new EntityNotFoundException(
						"Aucun type de produit avec l'ID = " +id+ " n'a ete trouvé dans la BDD", 
						ErrorCodes.PRODUCTTYPE_NOT_FOUND));
	}

	@Override
	public List<ProductTypeDto> findAll() {
		
		return productTypeRepository.findAll().stream()
				.map(ProductTypeDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<ProductTypeDto> findByNameLike(String search, Pageable pageable) {
		
		Page<ProductType> productTypes;
		if(search != null) {
			productTypes = productTypeRepository.findByNameLike(search, pageable);
		} else {
			productTypes = productTypeRepository.findAllProductTypes(pageable);
		}
		
		return productTypes.map(ProductTypeDto::fromEntity);
	}

	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Product Type ID is null");
		}
		
		List<Product> products = productRepository.findAllById(id);
		if(!products.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer un type de produit deja utilise dans une autre table",
					ErrorCodes.PRODUCTTYPE_ALREADY_EXISTS);
		}
		
		List<SalesUnit> salesUnits = salesUnitRepository.findAllById(id);
		if(!salesUnits.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer ce type de produit deja utilisé dans une autre table",
					ErrorCodes.PRODUCTTYPE_ALREADY_IN_USE);
		}
		
		List<MvtStkMenage> mvtStkMenage = mvtStkMenageRepository.findAllById(id);
		if(!mvtStkMenage.isEmpty() || !salesUnits.isEmpty() ) {
			throw new InvalidEntityException("Impossible de supprimer ce type de produit ayant au moins un mouvement de stock",
					ErrorCodes.PRODUCTTYPE_ALREADY_IN_USE);
		}
		
		productTypeRepository.deleteById(id);
		
	}
		
	
	
	

}
