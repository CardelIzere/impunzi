package com.refugietransaction.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
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
		
		Page<VenteListDto> findSupplierVentes(Date startDate, Date endDate, Long supplierId, String search, Pageable pageable);
		
		BigDecimal sumSupplierVentes(Date startDate, Date endDate, Long supplierId, String search);
		
		Page<VenteListDto> findSupplierAndCampVentes(Date startDate, Date endDate, Long supplierId, Long campId, String search, Pageable pageable);
		
		BigDecimal sumSupplierAndCampVentes(Date startDate, Date endDate, Long supplierId, Long campId, String search);

		void delete(Long id);
}
