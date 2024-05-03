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
import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.exceptions.InvalidOperationException;
import com.refugietransaction.model.Menage;
import com.refugietransaction.model.MouvementStock;
import com.refugietransaction.model.Product;
import com.refugietransaction.repository.MouvementStockRepository;
import com.refugietransaction.repository.ProductRepository;
import com.refugietransaction.services.ProductService;
import com.refugietransaction.validator.MenageValidator;
import com.refugietransaction.validator.MouvementStockValidator;
import com.refugietransaction.validator.ProductValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository produitRepository;
	private MouvementStockRepository mouvementStockRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository produitRepository, MouvementStockRepository mouvementStockRepository) {
		this.produitRepository = produitRepository;
		this.mouvementStockRepository = mouvementStockRepository;
	}
	
	@Override
	public ProductDto save(ProductDto dto) {
		
		List<String> errors = ProductValidator.validate(dto);
	    if (!errors.isEmpty()) {
	      log.error("Product is not valid {}", dto);
	      throw new InvalidEntityException("Le produit n'est pas valide", ErrorCodes.PRODUCT_NOT_VALID, errors);
	    }
	    
	    if(produitAlreadyExists(dto.getNomProduit())) {
	    	throw new InvalidEntityException("Un autre produit avec le meme nom existe deja", ErrorCodes.PRODUCT_ALREADY_EXISTS,
	    			Collections.singletonList("Un autre produit avec le meme nom existe deja dans la BDD"));
	    }
	    
		return ProductDto.fromEntity(
				produitRepository.save(ProductDto.toEntity(dto))
		);
	}
	
	private boolean produitAlreadyExists(String nom_produit) {
		Optional<Product> produit = produitRepository.findProduitByNom(nom_produit);
		return produit.isPresent();
	}

	@Override
	public ProductDto findById(Long id) {
		
		if (id == null) {
		      log.error("Produit ID is null");
		      return null;
		    }
		return produitRepository.findById(id)
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
		List<MouvementStock> mouvementStocks = mouvementStockRepository.findAllById(id);
		if(!mouvementStocks.isEmpty()) {
			throw new InvalidOperationException("Impossible de supprimer ce produit qui est deja utilisé",
					ErrorCodes.PRODUCT_ALREADY_IN_USE);
		}
		produitRepository.deleteById(id);
	}

	@Override
	public Page<ProductDto> findByNameProduitLike(String search, Pageable pageable) {
		Page<Product> produits;
		if(search != null) {
			produits = produitRepository.findByNameProduitLike(search, pageable);
		} else {
			produits = produitRepository.findAllProduits(pageable);
		}
		
		return produits.map(ProductDto::fromEntity);
	}

}
