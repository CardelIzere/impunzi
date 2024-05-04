package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.ProductTypeDto;

public class ProductTypeValidator {
	
	public static List<String> validate(ProductTypeDto productTypeDto){
		List<String> errors = new ArrayList<>();
		
		if(productTypeDto == null || !StringUtils.hasLength(productTypeDto.getName())) {
			errors.add("Veuillez renseigner le nom du type de produit");
		}
		
		if(productTypeDto.getSalesUnit() == null || productTypeDto.getSalesUnit().getId() == null || productTypeDto.getSalesUnit().getId().compareTo(0L) == 0) {
			errors.add("Veuillez selectionner une unité de vente");
		}
		
		return errors;
	}
}
