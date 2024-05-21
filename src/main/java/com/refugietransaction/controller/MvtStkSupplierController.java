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
import com.refugietransaction.dto.ByCampStockDto;
import com.refugietransaction.dto.CampStockDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductCampStockDto;
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

	@Override
	public List<ProductCampStockDto> getProductStockQuantities(Long productId) {
		
		return mvtStkSupplierService.findProductStockQuantityByCamp(productId);
	}

	@Override
	public Page<ByCampStockDto> getTotalQuantityByIdCampIdSupplier(Long idSupplier, Long idCamp, String search,
			int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.getProductsWithQuantityByIdCampIdSupplier(idCamp, idSupplier, search, pageable);
	}

	@Override
	public Page<MvtStkSupplierDto> getProductMvtStkByIdCampIdSupplier(LocalDate startDate, LocalDate endDate,
			Long idProduct, Long idSupplier, Long idCamp, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkSupplierService.getProductMvtStkBySupplierCamp(startDate, endDate, idProduct, idSupplier, idCamp, pageable);
	}

	

}
