package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Product;
import com.refugietransaction.model.Ventes;

public interface VentesRepository extends JpaRepository<Ventes, Long> {
	
	@Query("select v from Ventes v where v.id = :id")
	Ventes findVentesById(@Param("id") Long id);
	
	@Query("select v from Ventes v order by v.id desc ")
	Page<Ventes> findAllVentes(Pageable pageable);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where v.camp.id = :idCamp And v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by v.id desc")
	Page<Ventes> findByIdCampAndIdSupplierVentesByPersonneContactLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by v.id desc")
	Page<Ventes> findByIdSupplierVentesByPersonneContactLike(Long idSupplier, String search, Pageable pageable);
}
