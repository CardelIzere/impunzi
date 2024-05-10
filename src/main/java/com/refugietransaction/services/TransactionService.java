package com.refugietransaction.services;

import java.util.List;

import com.refugietransaction.dto.TransactionDto;

public interface TransactionService {
	
	TransactionDto save(TransactionDto dto);
	
	TransactionDto findById(Long id);
	
	List<TransactionDto> findAll();
	
	void delete(Long id);
}
