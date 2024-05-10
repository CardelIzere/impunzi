package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import com.refugietransaction.dto.TransactionDto;

public class TransactionValidator {
	
	public static List<String> validate(TransactionDto transactionDto){
		List<String> errors = new ArrayList<>();
		
		if(transactionDto ==null) {
			errors.add("Veuillez selectionner une vente");
		}
		
		if(transactionDto.getVentes() == null || transactionDto.getVentes().getId() == null || transactionDto.getVentes().getId().compareTo(0L) == 0) {
			errors.add("Veuillez selectionner une vente");
		}
		
		return errors;
	}
}
