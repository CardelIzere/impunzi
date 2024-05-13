package com.refugietransaction.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.CampDto;
import com.refugietransaction.dto.CampStockDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.dto.StockQuantityDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.Camp;
import com.refugietransaction.model.MvtStkSupplier;
import com.refugietransaction.model.Product;
import com.refugietransaction.model.TypeMvtStkSupplier;
import com.refugietransaction.repository.CampRepository;
import com.refugietransaction.repository.MvtStkSupplierRepository;
import com.refugietransaction.services.MvtStkSupplierService;
import com.refugietransaction.validator.MvtStkSupplierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MvtStkSupplierServiceImpl implements MvtStkSupplierService {
	
	private final MvtStkSupplierRepository mvtStkSupplierRepository;
	private final CampRepository campRepository;
	
	@Autowired
	public MvtStkSupplierServiceImpl(MvtStkSupplierRepository mvtStkSupplierRepository, CampRepository campRepository) {
		this.mvtStkSupplierRepository = mvtStkSupplierRepository;
		this.campRepository = campRepository;
	}

	@Override
	public MvtStkSupplierDto save(MvtStkSupplierDto dto) {
		List<String> errors = MvtStkSupplierValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Entries is not valid {}", dto);
			throw new InvalidEntityException("Le mouvement stock du fournisseur n'est pas valide", ErrorCodes.MVTSTK_SUPPLIER_NOT_VALID, errors);
		}
		
		dto.setDateMouvement(Instant.now());
		dto.setTypeMouvement(TypeMvtStkSupplier.ENTREE);
		
		return MvtStkSupplierDto.fromEntity(
				mvtStkSupplierRepository.save(MvtStkSupplierDto.toEntity(dto))
		);
	}

	@Override
	public Page<MvtStkSupplierDto> findCampSupplierEntriesByProductNameSupplierNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdCampAndIdSupplierEntriesByNameLike(idCamp, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findAllEntries(pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public MvtStkSupplierDto sortie(MvtStkSupplierDto dto) {
		List<String> errors = MvtStkSupplierValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Entries is not valid {}", dto);
			throw new InvalidEntityException("Le mouvement stock du fournisseur n'est pas valide", ErrorCodes.MVTSTK_SUPPLIER_NOT_VALID, errors);
		}
		
		dto.setDateMouvement(Instant.now());
		dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue()) * -1) );
		dto.setTypeMouvement(TypeMvtStkSupplier.SORTIE);
		
		return MvtStkSupplierDto.fromEntity(
				mvtStkSupplierRepository.save(MvtStkSupplierDto.toEntity(dto))
		);
	}

	@Override
	public List<MvtStkSupplierDto> findMvtStkSupplierBySupplierAndCamp(Long idSupplier, Long idCamp) {
		
		return mvtStkSupplierRepository.findMvtStkSupplierBySupplierAndCamp(idSupplier, idCamp).stream()
				.map(MvtStkSupplierDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MvtStkSupplierDto> findMvtStkSupplierByProductId(Long idProduit) {
		
		return mvtStkSupplierRepository.findMvtStkSupplierByProductId(idProduit).stream()
				.map(MvtStkSupplierDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<MvtStkSupplierDto> findAllByProductSupplierLike(String search, Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findAllByProductSupplierLike(search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findAllMvtStkSuppliers(pageable);
		}
		
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	

	@Override
	public Page<MvtStkSupplierDto> findCampSupplierSortiesByProductNameSupplierNameLike(Long idCamp, Long idSupplier,
			String search, Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdCampAndIdSupplierSortiesByNameLike(idCamp, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findAllSorties(pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public List<CampStockDto> findStockQuantityByCamp(Long supplierId) {
		List<Camp> camps =mvtStkSupplierRepository.findDistinctCampsBySupplierId(supplierId);

		List<CampStockDto> campStockDTOS=new ArrayList<>();
		for (Camp camp : camps) {
			//System.out.println("Camp: " + camp.getName());
			List<Object[]> results = mvtStkSupplierRepository.findStockQuantityByCamp(supplierId,camp);
			List<StockQuantityDto> stockQuantities=new ArrayList<>();
			for (Object[] result : results) {
				Product product = (Product) result[0];
				BigDecimal quantity = (BigDecimal) result[1];

				StockQuantityDto stockQuantityDTO = new StockQuantityDto();
				stockQuantityDTO.setProductName(product.getNomProduit());
				stockQuantityDTO.setQuantity(quantity);
				stockQuantities.add(stockQuantityDTO);


				//System.out.println("\tProduct: " + product.getName() + ", Quantity: " + quantity);
			}

			CampStockDto campStockDTO = new CampStockDto();
			campStockDTO.setCampName(camp.getNomCamp());
			campStockDTO.setStockQuantities(stockQuantities);
			campStockDTOS.add(campStockDTO);
		}
		return campStockDTOS;
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierEntriesByProductNameSupplierNameLike(Long idSupplier, String search,
			Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdSupplierEntriesByNameLike(idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findAllEntries(pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierSortiesByProductNameSupplierNameLike(Long idSupplier, String search,
			Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSupplier;
		if(search != null) {
			mvtStkSupplier = mvtStkSupplierRepository.findByIdSupplierSortiesByNameLike(idSupplier, search, pageable);
		} else {
			mvtStkSupplier = mvtStkSupplierRepository.findAllSorties(pageable);
		}
		return mvtStkSupplier.map(MvtStkSupplierDto::fromEntity);
	}

//	@Override
//	public BigDecimal stockReelMenage(Long idProduit, Long idMenage) {
//		
//		if(idProduit == null) {
//			log.warn("ID produit is NULL");
//			return BigDecimal.valueOf(-1);
//		}
//		
//		if(idMenage == null) {
//			log.warn("ID menage is NULL");
//			return BigDecimal.valueOf(-1);
//		}
//		
//		return mouvementStockRepository.stockReelMenage(idProduit, idMenage);
//	}
//
//	@Override
//	public List<MouvementStockSupplierDto> mvtStkArticleMenage(Long idProduit, Long idMenage) {
//		
//		return mouvementStockRepository.findAllByArticleIdAndMenageId(idProduit, idMenage).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public MouvementStockSupplierDto entreeStock(MouvementStockSupplierDto dto) {
//		return entreePositive(dto, TypeMouvementStock.ENTREE);
//	}
//
//	@Override
//	public MouvementStockSupplierDto sortieStock(MouvementStockSupplierDto dto) {
//		return sortiePositive(dto, TypeMouvementStock.SORTIE);
//	}
//	
//	private MouvementStockSupplierDto entreePositive(MouvementStockSupplierDto dto, TypeMouvementStock typeMouvement) {
//		
//		List<String> errors = MouvementStockSupplierValidator.validate(dto);
//		if(!errors.isEmpty()) {
//			log.error("Article is not valid {}", dto);
//			throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
//		}
//		dto.setQuantite(
//				BigDecimal.valueOf(
//						Math.abs(dto.getQuantite().doubleValue())
//						)
//				);
//		dto.setTypeMouvement(typeMouvement);
//		
//		return MouvementStockSupplierDto.fromEntity(
//				mouvementStockRepository.save(MouvementStockSupplierDto.toEntity(dto))
//		);
//	}
//	
//	private MouvementStockSupplierDto sortiePositive(MouvementStockSupplierDto dto, TypeMouvementStock typeMouvement) {
//		
//		List<String> errors = MouvementStockSupplierValidator.validate(dto);
//		if(!errors.isEmpty()) {
//			log.error("Article is not valid {}", dto);
//			throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
//		}
//		dto.setQuantite(
//				BigDecimal.valueOf(
//						Math.abs(dto.getQuantite().doubleValue())
//						)
//				);
//		dto.setTypeMouvement(typeMouvement);
//		
//		return MouvementStockSupplierDto.fromEntity(
//				mouvementStockRepository.save(MouvementStockSupplierDto.toEntity(dto))
//		);
//	}
//	
//	//Pour le camp
//
////	@Override
////	public List<MouvementStockDto> entreeArticleCamp(Long idProduit, Long idCamp) {
////		
////		return mouvementStockRepository.findEntreeByIdProduitIdCamp(idProduit, idCamp).stream()
////				.map(MouvementStockDto::fromEntity)
////				.collect(Collectors.toList());
////	}
////
////	@Override
////	public List<MouvementStockDto> sortieArticleCamp(Long idProduit, Long idCamp) {
////		
////		return mouvementStockRepository.findSortieByIdProduitIdCamp(idProduit, idCamp).stream()
////				.map(MouvementStockDto::fromEntity)
////				.collect(Collectors.toList());
////	}
////
////	@Override
////	public List<MouvementStockDto> entreeArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
////			Date endDate) {
////		
////		return mouvementStockRepository.findEntreeByIdProduitIdCampPeriode(idProduit, idCamp, startDate, endDate).stream()
////				.map(MouvementStockDto::fromEntity)
////				.collect(Collectors.toList());
////	}
////
////	@Override
////	public List<MouvementStockDto> sortieArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
////			Date endDate) {
////		
////		return mouvementStockRepository.findSortieByIdProduitIdCampPeriode(idProduit, idCamp, startDate, endDate).stream()
////				.map(MouvementStockDto::fromEntity)
////				.collect(Collectors.toList());
////	}
//	
//	//Pour le menage
//
//	@Override
//	public List<MouvementStockSupplierDto> entreeArticleMenage(Long idProduit, Long idMenage) {
//		
//		return mouvementStockRepository.findEntreeByIdProduitIdMenage(idProduit, idMenage).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<MouvementStockSupplierDto> sortieArticleMenage(Long idProduit, Long idMenage) {
//		
//		return mouvementStockRepository.findSortieByIdProduitIdMenage(idProduit, idMenage).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<MouvementStockSupplierDto> entreeArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockRepository.findEntreeByIdProduitIdMenagePeriode(idProduit, idMenage, startDate, endDate).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<MouvementStockSupplierDto> sortieArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockRepository.findSortieByIdProduitIdMenagePeriode(idProduit, idMenage, startDate, endDate).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//	
//	@Override
//	public List<MouvementStockSupplierDto> entreeArticleUser(Long idProduit, Long idUser) {
//		
//		return mouvementStockRepository.findEntreeByIdProduitIdAgent(idProduit, idUser).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<MouvementStockSupplierDto> sortieArticleUser(Long idProduit, Long idUser) {
//		
//		return mouvementStockRepository.findSortieByIdProduitIdAgent(idProduit, idUser).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<MouvementStockSupplierDto> entreeArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockRepository.findEntreeByIdProduitIdAgentPeriode(idProduit, idUser, startDate, endDate).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<MouvementStockSupplierDto> sortieArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockRepository.findSortieByIdProduitIdAgentPeriode(idProduit, idUser, startDate, endDate).stream()
//				.map(MouvementStockSupplierDto::fromEntity)
//				.collect(Collectors.toList());
//	}
	
	

}
