package com.refugietransaction.repository;

import java.math.BigDecimal;
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
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSupplierId(Date startDate, Date endDate, Long idSupplier, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier ")
	BigDecimal sumByStartDateAndEndDateAndSupplierId(Date startDate , Date endDate , Long idSupplier);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSearchAndSupplierId(Date startDate ,Date endDate, Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumByStartDateAndEndDateAndSearchAndSupplierId( Date startDate , Date endDate, Long idSupplier, String search);
	
	//list by id_supplier and id_camp
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where v.camp.id = :idCamp And v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by t.id desc")
	Page<Transaction> findCampSupplierTransactionByIdNumberLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.camp.id = :idCamp And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumBySupplierIdAndCampIdAndSearch(Long idSupplier, Long idCamp, String search);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And v.camp.id = :idCamp order by t.id desc")
	Page<Transaction> findBySupplierIdAndCampId(Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where v.supplier.id = :idSupplier And v.camp.id = :idCamp ")
	BigDecimal sumBySupplierIdAndCampId(Long idSupplier, Long idCamp);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSupplierIdAndCampId(Date startDate, Date endDate, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp")
	BigDecimal sumByStartDateAndEndDateAndSupplierIdAndCampId(Date startDate , Date endDate , Long idSupplier, Long idCamp);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by t.id desc")
	Page<Transaction> findByStartDateAndEndDateAndSearchAndSupplierIdAndCampId(Date startDate ,Date endDate, Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	@Query("SELECT SUM(lv.prixUnitaire * lv.quantite) FROM Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id LEFT JOIN v.ligneVentes lv where DATE_FORMAT(t.dateTransaction, '%Y-%m-%d') BETWEEN :startDate AND :endDate AND v.supplier.id = :idSupplier AND v.camp.id = :idCamp And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%')")
	BigDecimal sumByStartDateAndEndDateAndSearchAndSupplierIdAndCampId( Date startDate , Date endDate, Long idSupplier, Long idCamp, String search);
	
}
