package com.refugietransaction.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Magasinier;

public interface MagasinierRepository extends JpaRepository<Magasinier, Long> {
	
	List<Magasinier> findAllById(Long id);
	// JPQL query

	@Query(value = "select m from Magasinier m where m.id = :id")
	Magasinier findMagasinierById(@Param("id") Long id);

	@Query(value = "select ag from Magasinier ag where ag.supplier.id=?1 order by ag.id desc ")
	Page<Magasinier> findAllSupplierMagasiniers(Long idSupplier,Pageable pageable);

	@Query(value = "select ag from Magasinier ag join User u on ag.user.id = u.id where ag.supplier.id=?1 AND UPPER(u.userFullName) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userEmail) like CONCAT('%',UPPER(?2),'%' ) OR UPPER(u.userPhoneNumber) like CONCAT('%',UPPER(?2),'%' ) order by ag.id desc ")
	Page<Magasinier> findByNameEmailPhoneLike(Long idSupplier,String search, Pageable pageable);

	List<Magasinier> findAllByCampId(Long camp_id);
	
	@Query(value = "select m from Magasinier m order by m.id desc")
	Page<Magasinier> findAllMagasiniers(Pageable pageable);
}
