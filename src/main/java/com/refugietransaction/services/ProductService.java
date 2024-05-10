package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.ProductDto;

public interface ProductService {
	
	ProductDto save(ProductDto dto);
	
	ProductDto findById(Long id);
	
	List<ProductDto> findAll();
	
	Page<ProductDto> findByNameProduitLike(String search, Pageable pageable);
	
	Page<ProductDto> findSupplierProducts(Long idSupplier, String search, Pageable pageable);
	
	List<ProductDto> findAllSupplierProducts(Long idSupplier);
	
	List<ProductDto> findSoldProducts();
	
	void delete(Long id);
}
