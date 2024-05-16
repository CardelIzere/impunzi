package com.refugietransaction.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.MvtStkSupplierApi;
import com.refugietransaction.dto.CampStockDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.services.MvtStkSupplierService;

@RestController

public class MvtStkSupplierController implements MvtStkSupplierApi {
	
	private MvtStkSupplierService mvtStkSupplierService;
	
	@Autowired
	public MvtStkSupplierController(MvtStkSupplierService mvtStkSupplierService) {
		this.mvtStkSupplierService = mvtStkSupplierService;
	}

	@Override
	public MvtStkSupplierDto save(MvtStkSupplierDto dto) {
		
		return mvtStkSupplierService.save(dto);
	}

	@Override
	public Page<MvtStkSupplierDto> findAllEntries(Long idCamp, Long idSupplier, String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findCampSupplierEntriesByProductNameSupplierNameLike(idCamp, idSupplier, search, pageable);
	}

	@Override
	public List<MvtStkSupplierDto> findMvtStkSupplierBySupplierAndCamp(Long idCamp, Long idSupplier) {
		
		return mvtStkSupplierService.findMvtStkSupplierBySupplierAndCamp(idSupplier, idCamp);
	}

	@Override
	public List<MvtStkSupplierDto> findMvtStkSupplierByProductId(Long idProduit) {
		
		return mvtStkSupplierService.findMvtStkSupplierByProductId(idProduit);
	}

	@Override
	public Page<MvtStkSupplierDto> findAllMvtStkSuppliers(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findAllByProductSupplierLike(search, pageable);
	}

	@Override
	public List<CampStockDto> getStockQuantities(Long supplierId) {
		
		return mvtStkSupplierService.findStockQuantityByCamp(supplierId);
	}

	@Override
	public Page<MvtStkSupplierDto> findAllSorties(Long idCamp, Long idSupplier, String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findCampSupplierSortiesByProductNameSupplierNameLike(idCamp, idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findAllSupplierEntries(Long idSupplier, String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findSupplierEntriesByProductNameSupplierNameLike(idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findAllSupplierSorties(Long idSupplier, String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findSupplierSortiesByProductNameSupplierNameLike(idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierEntries(Long idSupplier, LocalDate startDate, LocalDate endDate,
			String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findSupplierEntries(startDate, endDate, idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierAndCampEntries(Long idSupplier, Long idCamp, LocalDate startDate,
			LocalDate endDate, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findSupplierAndCampEntries(startDate, endDate, idSupplier, idCamp, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierSorties(Long idSupplier, LocalDate startDate, LocalDate endDate,
			String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findSupplierSorties(startDate, endDate, idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierAndCampSorties(Long idSupplier, Long idCamp, LocalDate startDate,
			LocalDate endDate, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findSupplierAndCampSorties(startDate, endDate, idSupplier, idCamp, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductSupplierEntries(Long idSupplier, Long idProduct, LocalDate startDate,
			LocalDate endDate, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findProductSupplierEntries(startDate, endDate, idProduct, idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductAndSupplierAndCampEntries(Long idProduct, Long idSupplier, Long idCamp,
			LocalDate startDate, LocalDate endDate, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findProductCampSupplierEntries(startDate, endDate, idProduct, idCamp, idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductSupplierSorties(Long idProduct, Long idSupplier, LocalDate startDate,
			LocalDate endDate, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findProductSupplierSorties(startDate, endDate, idProduct, idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductAndSupplierAndCampSorties(Long idProduct, Long idSupplier, Long idCamp,
			LocalDate startDate, LocalDate endDate, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.findProductCampSupplierSorties(startDate, endDate, idProduct, idCamp, idSupplier, search, pageable);
	}

	

//	@Override
//	public BigDecimal stockReelMenage(Long idProduit, Long idMenage) {
//		return mouvementStockService.stockReelMenage(idProduit, idMenage);
//	}
//
//	@Override
//	public List<MvtStkSupplierDto> mvtStkArticleMenage(Long idProduit, Long idMenage) {
//		return mouvementStockService.mvtStkArticleMenage(idProduit, idMenage);
//	}
//
//	@Override
//	public MvtStkSupplierDto entreeStock(MvtStkSupplierDto dto) {
//		return mouvementStockService.entreeStock(dto);
//	}
//
//	@Override
//	public MvtStkSupplierDto sortieStock(MvtStkSupplierDto dto) {
//		return mouvementStockService.sortieStock(dto);
//	}
	
	//Pour le camp
	
//	@Override
//	public List<MouvementStockDto> entreeArticleCamp(Long idProduit, Long idCamp) {
//		return mouvementStockService.entreeArticleCamp(idProduit, idCamp);
//	}
//
//	@Override
//	public List<MouvementStockDto> sortieArticleCamp(Long idProduit, Long idCamp) {
//		return mouvementStockService.sortieArticleCamp(idProduit, idCamp);
//	}
//
//	@Override
//	public List<MouvementStockDto> entreeArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockService.entreeArticleCampPeriode(idProduit, idCamp, startDate, endDate);
//	}
//
//	@Override
//	public List<MouvementStockDto> sortieArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockService.sortieArticleCampPeriode(idProduit, idCamp, startDate, endDate);
//	}
	
	
	//Pour le menage
	
//	@Override
//	public List<MvtStkSupplierDto> entreeArticleMenage(Long idProduit, Long idMenage) {
//		
//		return mouvementStockService.entreeArticleMenage(idProduit, idMenage);
//	}
//
//	@Override
//	public List<MvtStkSupplierDto> sortieArticleMenage(Long idProduit, Long idMenage) {
//		
//		return mouvementStockService.sortieArticleMenage(idProduit, idMenage);
//	}
//
//	@Override
//	public List<MvtStkSupplierDto> entreeArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockService.entreeArticleMenagePeriode(idProduit, idMenage, startDate, endDate);
//	}
//
//	@Override
//	public List<MvtStkSupplierDto> sortieArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
//			Date endDate) {
//		
//		return mouvementStockService.sortieArticleMenagePeriode(idProduit, idMenage, startDate, endDate);
//	}
//	
//	
//	//Pour l'agent
//
//	@Override
//	public List<MvtStkSupplierDto> entreeArticleUser(Long idProduit, Long idUser) {
//			
//		return mouvementStockService.entreeArticleUser(idProduit, idUser);
//	}
//
//	@Override
//	public List<MvtStkSupplierDto> sortieArticleUser(Long idProduit, Long idUser) {
//			
//		return mouvementStockService.sortieArticleUser(idProduit, idUser);
//	}
//
//	@Override
//	public List<MvtStkSupplierDto> entreeArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
//			Date endDate) {
//			
//		return mouvementStockService.entreeArticleUserPeriode(idProduit, idUser, startDate, endDate);
//	}
//
//	@Override
//	public List<MvtStkSupplierDto> sortieArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
//			Date endDate) {
//			
//		return mouvementStockService.sortieArticleUserPeriode(idProduit, idUser, startDate, endDate);
//	}

}
