package com.refugietransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refugietransaction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
