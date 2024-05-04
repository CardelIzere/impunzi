package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.ProductApi;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.services.ProductService;

@RestController

public class ProductController implements ProductApi {
	
	private ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public ProductDto save(ProductDto dto) {
		return productService.save(dto);
	}

	@Override
	public ProductDto findById(Long idProduit) {
		return productService.findById(idProduit);
	}

	@Override
	public void delete(Long id) {
		productService.delete(id);
		
	}

	@Override
	public Page<ProductDto> findAllProduits(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productService.findByNameProduitLike(search, pageable);
	}

	@Override
	public List<ProductDto> findAll() {
		
		return productService.findAll();
	}

}
