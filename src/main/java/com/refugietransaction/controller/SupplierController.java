package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.SupplierApi;
import com.refugietransaction.dto.SupplierDto;
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
	public List<SupplierDto> findAll() {
		
		return supplierService.findAll();
	}

	@Override
	public void delete(Long id) {
		supplierService.delete(id);
		
	}

}
