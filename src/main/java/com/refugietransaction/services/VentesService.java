package com.refugietransaction.services;

import java.util.List;

import com.refugietransaction.dto.VentesDto;

public interface VentesService {
	
		VentesDto save(VentesDto dto);

		VentesDto findById(Long id);

		List<VentesDto> findAll();

		void delete(Long id);
}
