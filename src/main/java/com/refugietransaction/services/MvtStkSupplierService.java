package com.refugietransaction.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.CampStockDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.model.TypeMvtStkSupplier;

public interface MvtStkSupplierService {
	
	MvtStkSupplierDto save(MvtStkSupplierDto dto);
	
	MvtStkSupplierDto sortie(MvtStkSupplierDto dto);
	
	//Entries by camp_id and supplier_id
	Page<MvtStkSupplierDto> findCampSupplierEntriesByProductNameSupplierNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findSupplierAndCampEntries(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId, String search, Pageable pageable);
	
	//Entries by supplier_id
	Page<MvtStkSupplierDto> findSupplierEntriesByProductNameSupplierNameLike(Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findSupplierEntries(LocalDate startDate, LocalDate endDate, Long supplierId, String search, Pageable pageable);
	
	
	List<MvtStkSupplierDto> findMvtStkSupplierBySupplierAndCamp(Long idSupplier, Long idCamp);
	
	List<MvtStkSupplierDto> findMvtStkSupplierByProductId(Long idProduit);
	
	Page<MvtStkSupplierDto> findAllByProductSupplierLike(String search, Pageable pageable);
	
	//Sorties by camp_id and supplier_id
	Page<MvtStkSupplierDto> findCampSupplierSortiesByProductNameSupplierNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findSupplierAndCampSorties(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId, String search, Pageable pageable);
	
	//Sorties by supplier_id
	Page<MvtStkSupplierDto> findSupplierSortiesByProductNameSupplierNameLike(Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findSupplierSorties(LocalDate startDate, LocalDate endDate, Long supplierId, String search, Pageable pageable);
	
	List<CampStockDto> findStockQuantityByCamp(Long supplierId);
	
//	BigDecimal stockReelMenage(Long idProduit, Long idMenage);
//
//	List<MvtStkSupplierDto> mvtStkArticleMenage(Long idProduit, Long idMenage);
//
//	MvtStkSupplierDto entreeStock(MvtStkSupplierDto dto);
//
//	MvtStkSupplierDto sortieStock(MvtStkSupplierDto dto);
	
	//Pour le camp
	
//	List<MouvementStockDto> entreeArticleCamp(Long idProduit, Long idCamp);
//	
//	List<MouvementStockDto> sortieArticleCamp(Long idProduit, Long idCamp);
//	
//	List<MouvementStockDto> entreeArticleCampPeriode(Long idProduit, Long idCamp, Date startDate, Date endDate);
//	
//	List<MouvementStockDto> sortieArticleCampPeriode(Long idProduit, Long idCamp, Date startDate, Date endDate);
	
	//Pour le menage
	
//	List<MvtStkSupplierDto> entreeArticleMenage(Long idProduit, Long idMenage);
//	
//	List<MvtStkSupplierDto> sortieArticleMenage(Long idProduit, Long idMenage);
//	
//	List<MvtStkSupplierDto> entreeArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate, Date endDate);
//	
//	List<MvtStkSupplierDto> sortieArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate, Date endDate);
	
	
	//Pour l'utilisateur
	
//	List<MvtStkSupplierDto> entreeArticleUser(Long idProduit, Long idUser);
//		
//	List<MvtStkSupplierDto> sortieArticleUser(Long idProduit, Long idUser);
//		
//	List<MvtStkSupplierDto> entreeArticleUserPeriode(Long idProduit, Long idUser, Date startDate, Date endDate);
//		
//	List<MvtStkSupplierDto> sortieArticleUserPeriode(Long idProduit, Long idUser, Date startDate, Date endDate);
	
}
