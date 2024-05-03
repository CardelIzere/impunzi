package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	List<Admin> findAllById(Long id);

	@Query(value = "select u from Admin u where u.supplier.id = :supplierId AND u.adminTypeEnum = 'MAIN_ADMIN'")
	Optional<Admin> findMainAdminOfGivenSupplier(@Param("supplierId") Long supplierId);
	// JPQL query

	@Query(value = "select u from Admin u where u.id = :id")
	Admin findAdminById(@Param("id") Long id);

	@Query(value = "select ad from Admin ad where ad.adminTypeEnum = 'MAIN_ADMIN' order by ad.id desc ")
	Page<Admin> findAllMainAdmins(Pageable pageable);

	@Query(value = "select ad from Admin ad join User u on ad.user.id = u.id where ad.adminTypeEnum = 'MAIN_ADMIN' AND UPPER(u.userFullName) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(u.userEmail) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(u.userPhoneNumber) like CONCAT('%',UPPER(?1),'%' ) OR (UPPER(ad.supplier.name) like CONCAT('%',UPPER(?1),'%' ) AND ad.adminTypeEnum = 'MAIN_ADMIN' ) order by ad.id desc ")
	Page<Admin> findMainAdminsByNameEmailPhoneLike(String search, Pageable pageable);

	@Query(value = "select ad from Admin ad where ad.supplier.id=?1 order by ad.id desc ")
	Page<Admin> findSupplierAdmins(Long idSupplier,Pageable pageable);

	@Query(value = "select ad from Admin ad join User u on ad.user.id = u.id where ad.supplier.id=?1 AND UPPER(u.userFullName) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userEmail) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userPhoneNumber) like CONCAT('%',UPPER(?2),'%' )  order by ad.id desc ")
	Page<Admin> findSupplierAdminsByNameEmailPhoneLike(Long idSupplier,String search, Pageable pageable);

	List<Admin> findAllBySupplierId(Long supplier_id);
	
	@Query(value = "select a from Admin a order by a.id desc")
	Page<Admin> findAllAdmins(Pageable pageable);
}
