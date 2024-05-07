package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.weaver.loadtime.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.exceptions.InvalidOperationException;
import com.refugietransaction.model.Menage;
import com.refugietransaction.model.MvtStkSupplier;
import com.refugietransaction.model.Product;
import com.refugietransaction.repository.MvtStkSupplierRepository;
import com.refugietransaction.repository.ProductRepository;
import com.refugietransaction.services.ProductService;
import com.refugietransaction.validator.MenageValidator;
import com.refugietransaction.validator.MvtStkSupplierValidator;
import com.refugietransaction.validator.ProductValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	private MvtStkSupplierRepository mouvementStockRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, MvtStkSupplierRepository mouvementStockRepository) {
		this.productRepository = productRepository;
		this.mouvementStockRepository = mouvementStockRepository;
	}
	
	@Override
	public ProductDto save(ProductDto dto) {
		
		List<String> errors = ProductValidator.validate(dto);
	    if (!errors.isEmpty()) {
	      log.error("Product is not valid {}", dto);
	      throw new InvalidEntityException("Le produit n'est pas valide", ErrorCodes.PRODUCT_NOT_VALID, errors);
	    }
	    
	    if((dto.getId() == null || dto.getId().compareTo(0L) == 0)) {
	    	
	    	if(produitAlreadyExists(dto.getNomProduit())) {
		    	throw new InvalidEntityException("Un autre produit avec le meme nom existe deja", ErrorCodes.PRODUCT_ALREADY_EXISTS,
		    			Collections.singletonList("Un autre produit avec le meme nom existe deja dans la BDD"));
		    }
		    
			return ProductDto.fromEntity(
					productRepository.save(ProductDto.toEntity(dto))
			);
	    }
	    
	    Product existingProduct = productRepository.findProductById(dto.getId());
	    if(existingProduct != null && !existingProduct.getNomProduit().equals(dto.getNomProduit())) {
	    	
	    	if(produitAlreadyExists(dto.getNomProduit())) {
	    		throw new InvalidEntityException("Un autre produit avec le meme nom existe deja", ErrorCodes.PRODUCT_ALREADY_EXISTS, 
	    				Collections.singletonList("Un autre produit avec le meme nom existe deja dans la BDD"));
	    	}
	    }
	    
	    return ProductDto.fromEntity(
	    		productRepository.save(ProductDto.toEntity(dto))
	    );
	    
	    
	}
	
	private boolean produitAlreadyExists(String nom_produit) {
		Optional<Product> produit = productRepository.findProduitByNom(nom_produit);
		return produit.isPresent();
	}

	@Override
	public ProductDto findById(Long id) {
		
		if (id == null) {
		      log.error("Produit ID is null");
		      return null;
		    }
		return productRepository.findById(id)
				.map(ProductDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun produit avec l'ID = " +id+ " n' a ete trouve dans la BDD",
						ErrorCodes.PRODUCT_NOT_FOUND)
						);
	}

	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Produit ID is null");
		}
		List<MvtStkSupplier> mouvementStocks = mouvementStockRepository.findAllById(id);
		if(!mouvementStocks.isEmpty()) {
			throw new InvalidOperationException("Impossible de supprimer ce produit qui est deja utilisé",
					ErrorCodes.PRODUCT_ALREADY_IN_USE);
		}
		productRepository.deleteById(id);
	}

	@Override
	public Page<ProductDto> findByNameProduitLike(String search, Pageable pageable) {
		Page<Product> produits;
		if(search != null) {
			produits = productRepository.findByNameProduitLike(search, pageable);
		} else {
			produits = productRepository.findAllProduits(pageable);
		}
		
		return produits.map(ProductDto::fromEntity);
	}

	@Override
	public List<ProductDto> findAll() {
		
		return productRepository.findAll().stream()
				.map(ProductDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<ProductDto> findSupplierProducts(Long idSupplier, String search, Pageable pageable) {
		
		Page<Product> products;
		if(search != null) {
			products = productRepository.findSupplierProductsByNomProduitLike(idSupplier, search, pageable);
		} else {
			products = productRepository.findSupplierProducts(idSupplier, pageable);
		}
		
		return products.map(ProductDto::fromEntity);
	}

	@Override
	public List<ProductDto> findAllSupplierProducts(Long idSupplier) {
		
		return productRepository.findAllSupplierProducts(idSupplier).stream()
				.map(ProductDto::fromEntity)
				.collect(Collectors.toList());
	}

}
