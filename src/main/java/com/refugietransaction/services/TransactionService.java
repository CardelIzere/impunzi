package com.refugietransaction.services;

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
	
	void delete(Long id);
}
