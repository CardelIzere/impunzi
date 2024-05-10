package com.refugietransaction.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.refugietransaction.dto.LigneVenteDto;
import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.dto.VenteListDto;
import com.refugietransaction.dto.VentesDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.exceptions.InvalidOperationException;
import com.refugietransaction.model.LigneVente;
import com.refugietransaction.model.Product;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.model.TypeMvtStkMenageEnum;
import com.refugietransaction.model.TypeMvtStkSupplier;
import com.refugietransaction.model.VenteStatusEnum;
import com.refugietransaction.model.Ventes;
import com.refugietransaction.repository.LigneVenteRepository;
import com.refugietransaction.repository.ProductRepository;
import com.refugietransaction.repository.VentesRepository;
import com.refugietransaction.services.MvtStkMenageService;
import com.refugietransaction.services.MvtStkSupplierService;
import com.refugietransaction.services.VentesService;
import com.refugietransaction.validator.VentesValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {
	
	private final ProductRepository productRepository;
	private final VentesRepository ventesRepository;
	private final LigneVenteRepository ligneVenteRepository;
	private final MvtStkSupplierService mvtStkSupplierService;
	private final MvtStkMenageService mvtStkMenageService;
	
	@Autowired
	public VentesServiceImpl(ProductRepository productRepository, VentesRepository ventesRepository, LigneVenteRepository ligneVenteRepository, MvtStkSupplierService mvtStkSupplierService, MvtStkMenageService mvtStkMenageService) {
		this.productRepository = productRepository;
		this.ventesRepository = ventesRepository;
		this.ligneVenteRepository = ligneVenteRepository;
		this.mvtStkSupplierService = mvtStkSupplierService;
		this.mvtStkMenageService = mvtStkMenageService;
	}
	
	@Override
	public VentesDto save(VentesDto dto) {
		List<String> errors = VentesValidator.validate(dto);
	    if (!errors.isEmpty()) {
	      log.error("Ventes n'est pas valide");
	      throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
	    }
	    
	    List<String> productErrors = new ArrayList<>();
	    
	    dto.getLigneVentes().forEach(ligneVenteDto->{
	    	Optional<Product> product = productRepository.findById(ligneVenteDto.getProduct().getId());
	    	if(product.isEmpty()) {
	    		productErrors.add("Aucun produit avec l'ID" +ligneVenteDto.getProduct().getId()+ " n'a ete trouve dans la BDD");
	    	}
	    });
	    
	    if(!productErrors.isEmpty()) {
	    	log.error("One or more products were not found in the DB, {}", errors);
	    	throw new InvalidEntityException("Un ou plusieurs produits n'ont pas été trouvé dans la BDD", ErrorCodes.VENTE_NOT_VALID, errors);
	    }
	    
	    dto.setDateVente(Instant.now());
	    dto.setVenteStatusEnum(VenteStatusEnum.UNPAID);
	    
	    Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(dto));
	    
	    dto.getLigneVentes().forEach(ligneVenteDto->{
	    	LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
	    	ligneVente.setVente(savedVentes);
	    	ligneVenteRepository.save(ligneVente);
	    	updateMvtStkSupplier(ligneVente);
	    	updateMvtStkMenage(ligneVente);
	    });
	    
		return VentesDto.fromEntity(savedVentes);
	}

	private void updateMvtStkSupplier(LigneVente ligneVente) {
		MvtStkSupplierDto mvtStkSupplierDto = MvtStkSupplierDto.builder()
				.produit(ProductDto.fromEntity(ligneVente.getProduct()))
				.dateMouvement(Instant.now())
				.typeMouvement(TypeMvtStkSupplier.SORTIE)
				.quantite(ligneVente.getQuantite())
				.build();
		mvtStkSupplierService.sortie(mvtStkSupplierDto);
		
	}

	private void updateMvtStkMenage(LigneVente ligneVente) {
		MvtStkMenageDto mvtStkMenageDto = MvtStkMenageDto.builder()
				.productType(ProductTypeDto.fromEntity(ligneVente.getProduct().getProductType()))
				.dateMvt(Instant.now())
				.typeMvtStkMenage(TypeMvtStkMenageEnum.ACHAT)
				.quantite(ligneVente.getQuantite())
				.build();
		mvtStkMenageService.sortie(mvtStkMenageDto);
	}

	@Override
	public VentesDto findById(Long id) {
		if (id == null) {
		      log.error("Ventes ID is NULL");
		      return null;
		    }
		return ventesRepository.findById(id)
		    .map(VentesDto::fromEntity)
		    .orElseThrow(() -> new EntityNotFoundException("Aucun vente n'a ete trouve dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
	}

	@Override
	public List<VentesDto> findAll() {
		return ventesRepository.findAll().stream()
		        .map(VentesDto::fromEntity)
		        .collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
		      log.error("Vente ID is NULL");
		      return;
		    }
//		    List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
//		    if (!ligneVentes.isEmpty()) {
//		      throw new InvalidOperationException("Impossible de supprimer une vente ...",
//		          ErrorCodes.VENTE_ALREADY_IN_USE);
//		    }
		
			ligneVenteRepository.deleteById(id);
		    ventesRepository.deleteById(id);
		
	}

	@Override
	public Page<VenteListDto> findCampSupplierVentesBySupplierPersonneContactLike(Long idCamp, Long idSupplier,
			String search, Pageable pageable) {
		Page<Ventes> ventes;
		if(search != null) {
			ventes = ventesRepository.findByIdCampAndIdSupplierVentesByPersonneContactLike(idCamp, idSupplier, search, pageable);
		} else {
			ventes = ventesRepository.findAllVentes(pageable);
		}
		
		return ventes.map(VenteListDto::fromEntity);
	}

	@Override
	public List<LigneVenteDto> findSoldProductsInAllSales(Long idVente) {
		// TODO Auto-generated method stub
		return ligneVenteRepository.findSoldProductsInAllSales(idVente).stream()
				.map(LigneVenteDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<VenteListDto> findSupplierVentesByPersonneContactLike(Long idSupplier, String search,
			Pageable pageable) {
		Page<Ventes> ventes;
		if(search != null) {
			ventes = ventesRepository.findByIdSupplierVentesByPersonneContactLike(idSupplier, search, pageable);
		} else {
			ventes = ventesRepository.findAllVentes(pageable);
		}
		return ventes.map(VenteListDto::fromEntity);
	}

	

}
