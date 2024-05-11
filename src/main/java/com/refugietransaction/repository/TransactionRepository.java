package com.refugietransaction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refugietransaction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("select t from Transaction t order by t.id desc")
	Page<Transaction> findAllTransactions(Pageable pageable);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where v.camp.id = :idCamp And v.supplier.id = :idSupplier And UPPER(m.personneContact) like CONCAT('%',UPPER(:search),'%') order by t.id desc")
	Page<Transaction> findCampSupplierTransactionByIdNumberLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("select t from Transaction t join Ventes v on t.ventes.id = v.id join Menage m on v.menage.id = m.id where v.supplier.id = :idSupplier And CAST(m.idNumber AS string) like LOWER(CONCAT('%',UPPER(:search),'%')) order by t.id desc")
	Page<Transaction> findSupplierTransactionByIdNumberLike(Long idSupplier, String search, Pageable pageable);
}
