package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.SalesUnit;

public interface SalesUnitRepository extends JpaRepository<SalesUnit, Long> {
	
	List<SalesUnit> findAllById(Long id);
	// JPQL query
	  
	  @Query(value = "select su from SalesUnit su where su.id = :id")
	  SalesUnit findSalesUnitById(@Param("id") Long id);
	  
	  @Query(value = "select su from SalesUnit su where su.name = :name")
	  Optional<SalesUnit> findSalesUnitByName(@Param("name") String name);
	  
	  @Query(value = "select su from SalesUnit su order by su.id desc")
	  Page<SalesUnit> findAllSalesUnits(Pageable pageable);
	  
	  @Query(value = "select su from SalesUnit su where UPPER(su.name) like CONCAT('%', UPPER(?1), '%') order by su.id desc ")
	  Page<SalesUnit> findByNameLike(String search, Pageable pageable);
}
