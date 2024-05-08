package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.VenteListDto;
import com.refugietransaction.dto.VentesDto;

public interface VentesService {
	
		VentesDto save(VentesDto dto);

		VentesDto findById(Long id);

		List<VentesDto> findAll();
		
		Page<VenteListDto> findCampSupplierVentesBySupplierProductNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable);

		void delete(Long id);
}
