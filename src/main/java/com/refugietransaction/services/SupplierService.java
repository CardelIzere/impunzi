package com.refugietransaction.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.dto.SupplierListDto;

public interface SupplierService {
	
	SupplierDto save(SupplierDto dto);
	
	SupplierDto findById(Long id);
	
	Page<SupplierListDto> findByNamePhoneAddressLike(String search, Pageable pageable);
	
	void delete(Long id);
	
	void enableSupplier(Long supplierId);
	
	void desableSupplier(Long supplierId);
}
