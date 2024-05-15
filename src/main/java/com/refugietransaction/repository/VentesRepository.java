package com.refugietransaction.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
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
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by v.id desc")
	Page<Ventes> findAllByPersonneContactLike(String search, Pageable pageable);
	
	//listing by id_supplier
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by v.id desc")
	Page<Ventes> findByIdSupplierVentesByPersonneContactLike(Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumBySupplierIdAndSearch(Long idSupplier, String search);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And v.venteStatusEnum = 'PAID' order by v.id desc")
	Page<Ventes> findBySupplierId(Long idSupplier, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.venteStatusEnum = 'PAID' ")
	BigDecimal sumBySupplierId(Long idSupplier);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where TO_CHAR(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.venteStatusEnum = 'PAID' order by v.id desc")
	Page<Ventes> findByStartDateAndEndDateAndSupplierId(Date startDate, Date endDate, Long idSupplier, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where TO_DATE(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.venteStatusEnum = 'PAID'")
	BigDecimal sumByStartDateAndEndDateAndSupplierId(Date startDate , Date endDate , Long idSupplier);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where TO_CHAR(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by v.id desc")
	Page<Ventes> findByStartDateAndEndDateAndSearchAndSupplierId(Date startDate ,Date endDate, Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where TO_CHAR(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumByStartDateAndEndDateAndSearchAndSupplierId( Date startDate , Date endDate, Long idSupplier, String search);
	
	//listing by id_supplier and id_camp
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And v.camp.id = :idCamp And v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by v.id desc")
	Page<Ventes> findByIdSupplierIdCampVentesByPersonneContactLike(Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.camp.id = :idCamp And v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumBySupplierIdAndCampIdAndSearch(Long idSupplier, Long idCamp, String search);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And v.camp.id = :idCamp And v.venteStatusEnum = 'PAID' order by v.id desc")
	Page<Ventes> findBySupplierIdAndCampId(Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.camp.id = :idCamp And v.venteStatusEnum = 'PAID' ")
	BigDecimal sumBySupplierIdAndCampId(Long idSupplier, Long idCamp);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where TO_CHAR(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp AND v.venteStatusEnum = 'PAID' order by v.id desc")
	Page<Ventes> findByStartDateAndEndDateAndSupplierIdAndCampId(Date startDate, Date endDate, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where TO_CHAR(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp AND v.venteStatusEnum = 'PAID'")
	BigDecimal sumByStartDateAndEndDateAndSupplierIdAndCampId(Date startDate , Date endDate , Long idSupplier, Long idCamp);
	
	@Query("select v from Ventes v join Menage m on v.menage.id = m.id where TO_CHAR(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp AND v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by v.id desc")
	Page<Ventes> findByStartDateAndEndDateAndSearchAndSupplierIdAndCampId(Date startDate ,Date endDate, Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Ventes v join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where TO_CHAR(v.dateVente, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp AND v.venteStatusEnum = 'PAID' And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumByStartDateAndEndDateAndSearchAndSupplierIdAndCampId( Date startDate , Date endDate, Long idSupplier, Long idCamp, String search);
}
