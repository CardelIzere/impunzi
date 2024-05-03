package com.refugietransaction.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.ProductDto;

public interface ProductService {
	
	ProductDto save(ProductDto dto);
	
	ProductDto findById(Long id);
	
	Page<ProductDto> findByNameProduitLike(String search, Pageable pageable);
	
	void delete(Long id);
}
