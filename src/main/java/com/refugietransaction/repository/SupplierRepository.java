package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Query(value = "select s from Supplier s order by s.id desc")
	Page<Supplier> findAllSuppliers(Pageable pageable);
	
	@Query(value = "select s from Supplier s where UPPER(s.name) like CONCAT('%',UPPER(?1),'%') OR UPPER(s.phoneNumber) like CONCAT('%',UPPER(?1),'%') OR UPPER(s.address) like CONCAT('%',UPPER(?1),'%') order by s.id desc ")
	Page<Supplier> findByNamePhoneAddressLike(String search, Pageable pageable);
	
	@Query(" SELECT s FROM Supplier s " +
			" WHERE s.id NOT IN (" +
			" SELECT adm.supplier.id FROM Admin adm " +
			" WHERE adm.adminTypeEnum = 'MAIN_ADMIN' " +
			")")
	List<Supplier> findSuppliersWithNoMainAdmin();
}
