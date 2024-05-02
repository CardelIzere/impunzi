package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.weaver.loadtime.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.dto.ProduitDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.exceptions.InvalidOperationException;
import com.refugietransaction.model.Menage;
import com.refugietransaction.model.MouvementStock;
import com.refugietransaction.model.Produit;
import com.refugietransaction.repository.MouvementStockRepository;
import com.refugietransaction.repository.ProduitRepository;
import com.refugietransaction.services.ProduitService;
import com.refugietransaction.validator.MenageValidator;
import com.refugietransaction.validator.MouvementStockValidator;
import com.refugietransaction.validator.ProduitValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProduitServiceImpl implements ProduitService {
	
	private ProduitRepository produitRepository;
	private MouvementStockRepository mouvementStockRepository;
	
	@Autowired
	public ProduitServiceImpl(ProduitRepository produitRepository, MouvementStockRepository mouvementStockRepository) {
		this.produitRepository = produitRepository;
		this.mouvementStockRepository = mouvementStockRepository;
	}
	
	@Override
	public ProduitDto save(ProduitDto dto) {
		
		List<String> errors = ProduitValidator.validate(dto);
	    if (!errors.isEmpty()) {
	      log.error("Product is not valid {}", dto);
	      throw new InvalidEntityException("Le produit n'est pas valide", ErrorCodes.PRODUCT_NOT_VALID, errors);
	    }
	    
	    if(produitAlreadyExists(dto.getNomProduit())) {
	    	throw new InvalidEntityException("Un autre produit avec le meme nom existe deja", ErrorCodes.PRODUCT_ALREADY_EXISTS,
	    			Collections.singletonList("Un autre produit avec le meme nom existe deja dans la BDD"));
	    }
	    
		return ProduitDto.fromEntity(
				produitRepository.save(ProduitDto.toEntity(dto))
		);
	}
	
	private boolean produitAlreadyExists(String nom_produit) {
		Optional<Produit> produit = produitRepository.findProduitByNom(nom_produit);
		return produit.isPresent();
	}

	@Override
	public ProduitDto findById(Long id) {
		
		if (id == null) {
		      log.error("Produit ID is null");
		      return null;
		    }
		return produitRepository.findById(id)
				.map(ProduitDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun produit avec l'ID = " +id+ " n' a ete trouve dans la BDD",
						ErrorCodes.PRODUCT_NOT_FOUND)
						);
	}

	@Override
	public List<ProduitDto> findAll() {
		
		return produitRepository.findAll().stream()
				.map(ProduitDto::fromEntity)
				.collect(Collectors.toList());
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

}
