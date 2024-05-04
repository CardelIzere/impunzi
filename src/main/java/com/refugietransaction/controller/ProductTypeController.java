package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.ProductTypeApi;
import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.services.ProductTypeService;

@RestController
public class ProductTypeController implements ProductTypeApi {
	
	private final ProductTypeService productTypeService;
	
	@Autowired
	public ProductTypeController(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	
	@Override
	public ProductTypeDto save(ProductTypeDto dto) {
		
		return productTypeService.save(dto);
	}

	@Override
	public ProductTypeDto findById(Long idProductType) {
		
		return productTypeService.findById(idProductType);
	}

	@Override
	public List<ProductTypeDto> findAll() {
		
		return productTypeService.findAll();
	}

	@Override
	public Page<ProductTypeDto> findAllProductTypes(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productTypeService.findByNameProductTypeLike(search, pageable);
	}

	@Override
	public void delete(Long id) {
		productTypeService.delete(id);
		
	}

}
