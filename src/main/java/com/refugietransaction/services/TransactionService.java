package com.refugietransaction.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.TransactionDto;

public interface TransactionService {
	
	TransactionDto save(TransactionDto dto);
	
	TransactionDto findById(Long id);
	
	List<TransactionDto> findAll();
	
	Page<TransactionDto> findCampSupplierTransactionByPersonneContactLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	Page<TransactionDto> findSupplierTransactionByIdNumberLike(Long idSupplier, String search, Pageable pageable);
	
	Page<TransactionDto> findSupplierTransactions(LocalDate startDate, LocalDate endDate, Long supplierId, String search, Pageable pageable);
	
	BigDecimal sumSupplierTransactions(LocalDate startDate, LocalDate endDate, Long supplierId, String search);
	
	Page<TransactionDto> findSupplierAndCampTransactions(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId, String search, Pageable pageable);
	
	BigDecimal sumSupplierAndCampTransactions(LocalDate startDate, LocalDate endDate, Long supplierId, Long campId, String search);
	
	void delete(Long id);
}
