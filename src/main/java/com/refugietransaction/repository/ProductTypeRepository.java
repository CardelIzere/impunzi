package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
	
	List<ProductType> findAllById(Long id);
	// JPQL query
	  
	  @Query(value = "select p from ProductType p where p.id = :id")
	  ProductType findProductTypeById(@Param("id") Long id);
	  
	  @Query(value = "select p from ProductType p where p.nameProductType = :nameProductType")
	  Optional<ProductType> findProductTypeByName(@Param("nameProductType") String nameProductType);
	  
	  @Query(value = "select p from ProductType p order by p.id desc")
	  Page<ProductType> findAllProductTypes(Pageable pageable);
	  
	  @Query(value = "select p from ProductType p where UPPER(p.nameProductType) like CONCAT('%', UPPER(?1), '%') order by p.id desc ")
	  Page<ProductType> findByNameProductTypeLike(String search, Pageable pageable);
}
