package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.SupplierApi;
import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.dto.SupplierListDto;
import com.refugietransaction.services.SupplierService;

@RestController
public class SupplierController implements SupplierApi {
	
	private final SupplierService supplierService;
	
	@Autowired
	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	
	@Override
	public SupplierDto save(SupplierDto dto) {
		
		return supplierService.save(dto);
	}

	@Override
	public SupplierDto findById(Long id) {
		
		return supplierService.findById(id);
	}

	@Override
	public void delete(Long id) {
		supplierService.delete(id);
		
	}

	@Override
	public Page<SupplierListDto> findAllSuppliers(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return supplierService.findByNamePhoneAddressLike(search, pageable);
	}

	@Override
	public void enableSupplier(Long id) {
		supplierService.enableSupplier(id);
		
	}

	@Override
	public void desableSupplier(Long id) {
		supplierService.desableSupplier(id);
		
	}

	@Override
	public List<SupplierDto> findSuppliersWithNoMainAdmin() {
		
		return supplierService.findSuppliersWithNoMainAdmin();
	}

	@Override
	public List<SupplierDto> findAll() {
		
		return supplierService.findAll();
	}

}
