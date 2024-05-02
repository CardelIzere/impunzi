package com.refugietransaction.services;

import java.util.List;

import com.refugietransaction.dto.SupplierDto;

public interface SupplierService {
	
	SupplierDto save(SupplierDto dto);
	
	SupplierDto findById(Long id);
	
	List<SupplierDto> findAll();
	
	void delete(Long id);
}
