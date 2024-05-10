package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.TransactionApi;
import com.refugietransaction.dto.TransactionDto;
import com.refugietransaction.services.TransactionService;

@RestController
public class TransactionController implements TransactionApi {
	
	private final TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@Override
	public TransactionDto save(TransactionDto dto) {
		
		return transactionService.save(dto);
	}

	@Override
	public TransactionDto findById(Long idTransaction) {
		
		return transactionService.findById(idTransaction);
	}

	@Override
	public List<TransactionDto> findAll() {
		
		return transactionService.findAll();
	}

	@Override
	public void delete(Long id) {
		
		transactionService.delete(id);
		
	}

}
