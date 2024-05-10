package com.refugietransaction.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.TransactionDto;
import com.refugietransaction.repository.TransactionRepository;
import com.refugietransaction.services.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
	
	private final TransactionRepository transactionRepository;
	
	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	@Override
	public TransactionDto save(TransactionDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionDto findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
