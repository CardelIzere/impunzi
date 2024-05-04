package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.ProductTypeDto;

public interface ProductTypeService {
	
	ProductTypeDto save(ProductTypeDto dto);
	
	ProductTypeDto findById(Long id);
	
	List<ProductTypeDto> findAll();
	
	Page<ProductTypeDto> findByNameProductTypeLike(String search, Pageable pageable);
	
	void delete(Long id);
}
