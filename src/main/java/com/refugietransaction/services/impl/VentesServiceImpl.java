package com.refugietransaction.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.refugietransaction.dto.CampDto;
import com.refugietransaction.dto.LigneVenteDto;
import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.dto.SupplierDto;
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
import com.refugietransaction.repository.MvtStkMenageRepository;
import com.refugietransaction.repository.MvtStkSupplierRepository;
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
	private final MvtStkSupplierRepository mvtStkSupplierRepository;
	private final MvtStkMenageRepository mvtStkMenageRepository;
	
	@Autowired
	public VentesServiceImpl(ProductRepository productRepository, VentesRepository ventesRepository, LigneVenteRepository ligneVenteRepository, MvtStkSupplierService mvtStkSupplierService, MvtStkMenageService mvtStkMenageService, MvtStkSupplierRepository mvtStkSupplierRepository, MvtStkMenageRepository mvtStkMenageRepository) {
		this.productRepository = productRepository;
		this.ventesRepository = ventesRepository;
		this.ligneVenteRepository = ligneVenteRepository;
		this.mvtStkSupplierService = mvtStkSupplierService;
		this.mvtStkMenageService = mvtStkMenageService;
		this.mvtStkSupplierRepository = mvtStkSupplierRepository;
		this.mvtStkMenageRepository = mvtStkMenageRepository;
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
	    	Optional<Product> productOpt = productRepository.findById(ligneVenteDto.getProduct().getId());
	    	if(productOpt.isEmpty()) {
	    		productErrors.add("Aucun produit avec l'ID" +ligneVenteDto.getProduct().getId()+ " n'a ete trouve dans la BDD");
	    	} else {
	    		Product product = productOpt.get();
	    		BigDecimal inStockQuantity = getInStockQuantity(product.getId());
	    		BigDecimal inStockProductTypeQuantity = getInStockProductTypeQuantity(dto.getMenage().getId(), product.getProductType().getId());
	    		
	    		if(ligneVenteDto.getQuantite().compareTo(inStockQuantity) > 0) {
	    			throw new InvalidEntityException("La quantite demandée pour le produit " + product.getNomProduit() + " est supérieure à la quantité en stock chez le fournisseur.", ErrorCodes.VENTE_NOT_VALID);

	    		} else if(ligneVenteDto.getQuantite().compareTo(inStockProductTypeQuantity) > 0) {
	    			throw new InvalidEntityException("La quantite demandée pour le type produit " + product.getProductType().getName() + " est supérieur à la quantité en stock chez le ménage.", ErrorCodes.VENTE_NOT_VALID);

	    		}
	    	}
	    });
	    
	    if(!productErrors.isEmpty()) {
	    	log.error("One or more products were not found in the DB, {}", errors);
	    	throw new InvalidEntityException("Un ou plusieurs produits n'ont pas été trouvé dans la BDD", ErrorCodes.VENTE_NOT_VALID, errors);
	    }
	    
	    dto.setSaleCode(transactionCodePrefix()+generateTransactionCode(6));
	    dto.setDateVente(LocalDate.now());
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
	
	
	
	private BigDecimal getInStockProductTypeQuantity(Long menageId, Long productTypeId) {
		BigDecimal inStockProductTypeQuantity = mvtStkMenageRepository.findTotalQuantityByMenageIdAndProductTypeId(menageId, productTypeId);
		return inStockProductTypeQuantity != null ? inStockProductTypeQuantity : BigDecimal.ZERO;
	}

	private BigDecimal getInStockQuantity(Long productId) {
		BigDecimal inStockQuantity = mvtStkSupplierRepository.findTotalQuantityByProductId(productId);
		return inStockQuantity != null ? inStockQuantity : BigDecimal.ZERO;
	}

	public static String generateTransactionCode(int length) {
		Random random = new Random();
		StringBuilder accountNumber = new StringBuilder();
		for(int i=0;i<length;i++) {
			accountNumber.append(random.nextInt(10));
		}
		
		return accountNumber.toString();
	}
	
	public static String transactionCodePrefix() {
		LocalDate currentDate = LocalDate.now();
		int year = currentDate.getYear();
		int month = currentDate.getMonthValue();
		int day = currentDate.getDayOfMonth();
		
		return String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
	}

	private void updateMvtStkSupplier(LigneVente ligneVente) {
		MvtStkSupplierDto mvtStkSupplierDto = MvtStkSupplierDto.builder()
				.produit(ProductDto.fromEntity(ligneVente.getProduct()))
				.supplier(SupplierDto.fromEntity(ligneVente.getProduct().getSupplier()))
				.camp(CampDto.fromEntity(ligneVente.getVente().getCamp()))
				.dateMouvement(LocalDate.now())
				.typeMouvement(TypeMvtStkSupplier.SORTIE)
				.quantite(ligneVente.getQuantite())
				.build();
		mvtStkSupplierService.sortie(mvtStkSupplierDto);
		
	}

	private void updateMvtStkMenage(LigneVente ligneVente) {
		MvtStkMenageDto mvtStkMenageDto = MvtStkMenageDto.builder()
				.productType(ProductTypeDto.fromEntity(ligneVente.getProduct().getProductType()))
				.menage(MenageDto.fromEntity(ligneVente.getVente().getMenage()))
				.dateMvt(LocalDate.now())
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
			
			Ventes ventes = ventesRepository.findVentesById(id);
			
//			if(ventes.getVenteStatusEnum() == VenteStatusEnum.PAID) {
//				throw new InvalidOperationException("Impossible de supprimer une vente car il est deja payé", 
//						ErrorCodes.VENTE_ALREADY_PAID);
//			}
			
			List<LigneVente> ligneVentes = ligneVenteRepository.findLigneVenteByIdVente(id);
			
			for(LigneVente ligneVente : ligneVentes) {
				ligneVenteRepository.delete(ligneVente);
			}
		
		    ventesRepository.delete(ventes);
		
	}

	@Override
	public Page<VenteListDto> findCampSupplierVentesBySupplierPersonneContactLike(Long idSupplier, Long idCamp,
			String search, Pageable pageable) {
		Page<Ventes> ventes;
		if(search != null) {
			ventes = ventesRepository.findByIdSupplierIdCampVentesByPersonneContactLike(idSupplier, idCamp, search, pageable);
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

	@Override
	public Page<VenteListDto> findSupplierVentes(LocalDate startDate, LocalDate endDate, Long supplierId, String search,
			Pageable pageable) {
		Page<Ventes> ventes=null;
		if(startDate == null && endDate == null) {
			//If both startDate and endDate are null, return all records without date filtering 
			if(search == null || search.isEmpty()) {
				ventes = ventesRepository.findBySupplierId(supplierId, pageable);
			} else {
				ventes = ventesRepository.findByIdSupplierVentesByPersonneContactLike(supplierId, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				ventes = ventesRepository.findByStartDateAndEndDateAndSupplierId(startDate, endDate, supplierId, pageable);
			} else {
				ventes = ventesRepository.findByStartDateAndEndDateAndSearchAndSupplierId(startDate, endDate, supplierId, search, pageable);
			}
		}
		return ventes.map(VenteListDto::fromEntity);
	}

	@Override
	public BigDecimal sumSupplierVentes(LocalDate startDate, LocalDate endDate, Long supplierId, String search) {
		
		BigDecimal totalSum;
		if(startDate == null && endDate == null) {
			//if both startDate and endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				totalSum = ventesRepository.sumBySupplierId(supplierId);
			} else {
				totalSum = ventesRepository.sumBySupplierIdAndSearch(supplierId, search);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				totalSum = ventesRepository.sumByStartDateAndEndDateAndSupplierId(startDate, endDate, supplierId);
			} else {
				totalSum = ventesRepository.sumByStartDateAndEndDateAndSearchAndSupplierId(startDate, endDate, supplierId, search);
			}
		}
		return totalSum;
	}

	@Override
	public Page<VenteListDto> findSupplierAndCampVentes(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId,
			String search, Pageable pageable) {
		Page<Ventes> ventes=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				ventes = ventesRepository.findBySupplierIdAndCampId(supplierId, campId, pageable);
			} else {
				ventes = ventesRepository.findByIdSupplierIdCampVentesByPersonneContactLike(supplierId, campId, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				ventes = ventesRepository.findByStartDateAndEndDateAndSupplierIdAndCampId(startDate, endDate, supplierId, campId, pageable);
			} else {
				ventes = ventesRepository.findByStartDateAndEndDateAndSearchAndSupplierIdAndCampId(startDate, endDate, supplierId, campId, search, pageable);
			}
		}
		return ventes.map(VenteListDto::fromEntity);
	}

	@Override
	public BigDecimal sumSupplierAndCampVentes(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId,
			String search) {
		BigDecimal totalSum;
		if(startDate == null && endDate == null) {
			//if both startDate and endDate are null, return all records without filtering 
			if(search == null || search.isEmpty()) {
				totalSum = ventesRepository.sumBySupplierIdAndCampId(supplierId, campId);
			} else {
				totalSum = ventesRepository.sumBySupplierIdAndCampIdAndSearch(supplierId, campId, search);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				totalSum = ventesRepository.sumByStartDateAndEndDateAndSupplierIdAndCampId(startDate, endDate, supplierId, campId);
			} else {
				totalSum = ventesRepository.sumByStartDateAndEndDateAndSearchAndSupplierIdAndCampId(startDate, endDate, supplierId, campId, search);
			}
		}
		return totalSum;
	}

	

	

}
