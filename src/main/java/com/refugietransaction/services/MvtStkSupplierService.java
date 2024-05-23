package com.refugietransaction.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.ByCampStockDto;
import com.refugietransaction.dto.CampStockDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductCampStockDto;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.dto.ProductStockQuantityDto;
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
	
	
	//-----------------------
	
	List<MvtStkSupplierDto> findMvtStkSupplierBySupplierAndCamp(Long idSupplier, Long idCamp);
	
	List<MvtStkSupplierDto> findMvtStkSupplierByProductId(Long idProduit);
	
	Page<MvtStkSupplierDto> findAllByProductSupplierLike(String search, Pageable pageable);
	
	
	//Sorties by camp_id and supplier_id
	Page<MvtStkSupplierDto> findCampSupplierSortiesByProductNameSupplierNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findSupplierAndCampSorties(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId, String search, Pageable pageable);
	
	//Sorties by supplier_id
	Page<MvtStkSupplierDto> findSupplierSortiesByProductNameSupplierNameLike(Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findSupplierSorties(LocalDate startDate, LocalDate endDate, Long supplierId, String search, Pageable pageable);
	
	//Entries by product_id, camp_id and supplier_id
	Page<MvtStkSupplierDto> findProductCampSupplierEntriesByProductNameSupplierNameLike(Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findProductCampSupplierEntries(LocalDate startDate, LocalDate endDate, Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	//Entries by product_id and supplier_id
	Page<MvtStkSupplierDto> findProductSupplierEntriesByProductNameSupplierNameLike(Long idProduct, Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findProductSupplierEntries(LocalDate startDate, LocalDate endDate, Long idProduct, Long idSupplier, String search, Pageable pageable);
	
	//Sorties by product_id, camp_id and supplier_id
	Page<MvtStkSupplierDto> findProductCampSupplierSortiesByProductNameSupplierNameLike(Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findProductCampSupplierSorties(LocalDate startDate, LocalDate endDate, Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	//Sorties by product_id and supplier_id
	Page<MvtStkSupplierDto> findProductSupplierSortiesByProductNameSupplierNameLike(Long idProduit, Long idSupplier, String search, Pageable pageable);
	Page<MvtStkSupplierDto> findProductSupplierSorties(LocalDate startDate, LocalDate endDate, Long idProduct, Long idSupplier, String search, Pageable pageable);
	
	//----------------
	
	List<CampStockDto> findStockQuantityByCamp(Long supplierId);
	
	List<ProductStockQuantityDto> findProductStockQuantityByCamp(Long productId);
	
	Page<ByCampStockDto> getProductsWithQuantityByIdCampIdSupplier(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	Page<MvtStkSupplierDto> getProductMvtStkBySupplierCamp(LocalDate startDate, LocalDate endDate, Long idProduct, Long idSupplier, Long idCamp, Pageable pageable);
	
	
	void delete(Long id);
	
}
