package com.refugietransaction.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.LigneVenteDto;
import com.refugietransaction.dto.VenteListDto;
import com.refugietransaction.dto.VentesDto;

public interface VentesService {
	
		VentesDto save(VentesDto dto);

		VentesDto findById(Long id);

		List<VentesDto> findAll();
		
		Page<VenteListDto> findCampSupplierVentesBySupplierPersonneContactLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
		
		List<LigneVenteDto> findSoldProductsInAllSales(Long idVente);
		
		Page<VenteListDto> findSupplierVentesByPersonneContactLike(Long idSupplier, String search, Pageable pageable);
		
		Page<VenteListDto> findSupplierVentes(LocalDate startDate, LocalDate endDate, Long supplierId, String search, Pageable pageable);
		
		BigDecimal sumSupplierVentes(LocalDate startDate, LocalDate endDate, Long supplierId, String search);
		
		Page<VenteListDto> findSupplierAndCampVentes(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId, String search, Pageable pageable);
		
		BigDecimal sumSupplierAndCampVentes(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId, String search);

		void delete(Long id);
}
