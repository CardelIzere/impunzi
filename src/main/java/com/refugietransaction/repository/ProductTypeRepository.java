package com.refugietransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refugietransaction.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
	
	
}
