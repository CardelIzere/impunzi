package com.refugietransaction.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refugietransaction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("select t from Transaction t order by t.id desc")
	Page<Transaction> findAllTransactions(Pageable pageable);
	
	
	
	//list by id_supplier
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And CAST(m.idNumber AS string) like LOWER(CONCAT('%',UPPER(:search),'%')) order by t.id desc")
	Page<Transaction> findSupplierTransactionByIdNumberLike(Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumBySupplierIdAndSearch(Long idSupplier, String search);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id where v.supplier.id = :idSupplier order by t.id desc")
	Page<Transaction> findBySupplierId(Long idSupplier, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier ")
	BigDecimal sumBySupplierId(Long idSupplier);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSupplierId(LocalDate startDate, LocalDate endDate, Long idSupplier, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier ")
	BigDecimal sumByStartDateAndEndDateAndSupplierId(LocalDate startDate , LocalDate endDate , Long idSupplier);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSearchAndSupplierId(LocalDate startDate ,LocalDate endDate, Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumByStartDateAndEndDateAndSearchAndSupplierId( LocalDate startDate , LocalDate endDate, Long idSupplier, String search);
	
	//list by id_supplier and id_camp
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where v.camp.id = :idCamp And v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by t.id desc")
	Page<Transaction> findCampSupplierTransactionByIdNumberLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.camp.id = :idCamp And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumBySupplierIdAndCampIdAndSearch(Long idSupplier, Long idCamp, String search);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And v.camp.id = :idCamp order by t.id desc")
	Page<Transaction> findBySupplierIdAndCampId(Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.camp.id = :idCamp ")
	BigDecimal sumBySupplierIdAndCampId(Long idSupplier, Long idCamp);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSupplierIdAndCampId(LocalDate startDate, LocalDate endDate, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp")
	BigDecimal sumByStartDateAndEndDateAndSupplierIdAndCampId(LocalDate startDate , LocalDate endDate , Long idSupplier, Long idCamp);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSearchAndSupplierIdAndCampId(LocalDate startDate ,LocalDate endDate, Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where t.dateTransaction BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumByStartDateAndEndDateAndSearchAndSupplierIdAndCampId( LocalDate startDate , LocalDate endDate, Long idSupplier, Long idCamp, String search);
	
}
