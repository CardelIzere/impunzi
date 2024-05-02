package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	List<Supplier> findAll();
	
	@Query(value = "select s from Supplier s where s.name = :name")
	Optional<Supplier> findSupplierByName(@Param("name") String name);
	
	@Query(value = "select s from Supplier s where s.id = :id")
	Supplier findSupplierById(@Param("id") Long id);
	
	@Query(value = "select s from Supplier s where s.phoneNumber = :phoneNumber")
	Optional<Supplier> findSupplierByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
}
