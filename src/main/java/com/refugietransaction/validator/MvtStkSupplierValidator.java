package com.refugietransaction.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.MvtStkSupplierDto;

public class MvtStkSupplierValidator {
	public static List<String> validate(MvtStkSupplierDto mouvementStockSupplierDto){
		List<String> errors = new ArrayList<>();
		
		if (mouvementStockSupplierDto == null){

			errors.add("Veillez selectionner un produit");

		}
		
		
		if(mouvementStockSupplierDto.getProduit() == null || mouvementStockSupplierDto.getProduit().getId() == null) {
			errors.add("Veillez selectionner un produit");
		}
		
		return errors;
	}
}
