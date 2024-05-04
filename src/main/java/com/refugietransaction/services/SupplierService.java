package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.dto.SupplierListDto;

public interface SupplierService {
	
	SupplierDto save(SupplierDto dto);
	
	SupplierDto findById(Long id);
	
	List<SupplierDto> findAll();
	
	Page<SupplierListDto> findByNamePhoneAddressLike(String search, Pageable pageable);
	
	void delete(Long id);
	
	List<SupplierDto> findSuppliersWithNoMainAdmin();
	
	void enableSupplier(Long supplierId);
	
	void desableSupplier(Long supplierId);
}
