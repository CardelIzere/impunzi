package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.refugietransaction.model.Transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
	
	private Long id;
	private String transactionCode;
	private LocalDate dateTransaction;
	private BigDecimal montantTransaction;
	private VentesDto ventes;
	
	public static TransactionDto fromEntity(Transaction transaction) {
			if(transaction == null) {
				return null;
			}
			
			return TransactionDto.builder()
					.id(transaction.getId())
					.transactionCode(transaction.getTransactionCode())
					.dateTransaction(transaction.getDateTransaction())
					.montantTransaction(transaction.getMontantTransaction())
					.ventes(VentesDto.fromEntity(transaction.getVentes()))
					.build();
	}
	
	public static Transaction toEntity(TransactionDto transactionDto) {
		if(transactionDto == null) {
			return null;
		}
		
		Transaction transaction = new Transaction();
		transaction.setId(transactionDto.getId());
		transaction.setTransactionCode(transactionDto.getTransactionCode());
		transaction.setDateTransaction(transactionDto.getDateTransaction());
		transaction.setMontantTransaction(transactionDto.getMontantTransaction());
		transaction.setVentes(VentesDto.toEntity(transactionDto.getVentes()));
		
		return transaction;
	}
}
