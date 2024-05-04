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
import com.refugietransaction.model.Product;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.repository.ProductRepository;
import com.refugietransaction.repository.ProductTypeRepository;
import com.refugietransaction.services.ProductTypeService;
import com.refugietransaction.validator.ProductTypeValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductTypeServiceImpl implements ProductTypeService {
	
	private final ProductTypeRepository productTypeRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository, ProductRepository productRepository) {
		this.productTypeRepository = productTypeRepository;
		this.productRepository = productRepository;
	}

	@Override
	public ProductTypeDto save(ProductTypeDto dto) {
		List<String> errors = ProductTypeValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Product Type is not valid {}", dto);
			throw new InvalidEntityException("Le type de produit n'est pas valide", ErrorCodes.PRODUCTTYPE_NOT_VALID, errors);
		}
		
		if((dto.getId() == null || dto.getId().compareTo(0L) == 0)) {
			
			if(productTypeAlreadyExists(dto.getNameProductType())) {
				throw new InvalidEntityException("Un autre type de produit avec le meme nom existe deja", ErrorCodes.PRODUCTTYPE_ALREADY_EXISTS, 
						Collections.singletonList("Un autre type de produit avec le meme nom existe deja dans la BDD"));
			}
			
			return ProductTypeDto.fromEntity(
					productTypeRepository.save(ProductTypeDto.toEntity(dto))
			);
		}
		
		ProductType existingProductType = productTypeRepository.findProductTypeById(dto.getId());
		if(existingProductType != null && !existingProductType.getNameProductTpe().equals(dto.getNameProductType())) {
			
			if(productTypeAlreadyExists(dto.getNameProductType())) {
				throw new InvalidEntityException("Un autre type de produit avec le meme nom existe deja", ErrorCodes.PRODUCTTYPE_ALREADY_EXISTS, 
						Collections.singletonList("Un autre type de produit avec le meme nom existe deja dans la BDD"));
			}
		}
		
		return ProductTypeDto.fromEntity(
				productTypeRepository.save(ProductTypeDto.toEntity(dto))
		);
	}

	private boolean productTypeAlreadyExists(String nameProductType) {
		Optional<ProductType> productType = productTypeRepository.findProductTypeByName(nameProductType);
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
	public Page<ProductTypeDto> findByNameProductTypeLike(String search, Pageable pageable) {
		
		Page<ProductType> productTypes;
		if(search != null) {
			productTypes = productTypeRepository.findByNameProductTypeLike(search, pageable);
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
		
		List<Product> product = productRepository.findAllByProductTypeId(id);
		if(!product.isEmpty()) {
			throw new InvalidEntityException("Impossible de supprimer ce type de produit deja utilisé", 
					ErrorCodes.PRODUCTTYPE_ALREADY_IN_USE);
		}
		
		productTypeRepository.deleteById(id);
		
	}
		
	
	
	

}
