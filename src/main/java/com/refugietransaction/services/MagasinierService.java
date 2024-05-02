package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.MagasinierDto;

public interface MagasinierService {
	
	MagasinierDto save(MagasinierDto dto);
	
	MagasinierDto findById(Long id);
	
	List<MagasinierDto> findAll();

	Page<MagasinierDto> findSupplierMagasiniers(Long idSupplier, String search, Pageable pageable);
	
	void delete(Long id);
}
