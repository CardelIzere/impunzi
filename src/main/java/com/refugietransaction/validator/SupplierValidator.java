package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.SupplierDto;

public class SupplierValidator {
	
	public static List<String> validate(SupplierDto supplierDto){
		List<String> errors = new ArrayList<>();
		
		if(supplierDto == null) {
			errors.add("Veuillez renseigner un nom");
			errors.add("Veuillez renseigner un numero de telephone");
			errors.add("Veuillez renseigner une adresse");
		}
		
		if(!StringUtils.hasLength(supplierDto.getName())) {
			errors.add("Veuillez renseigner un nom");
		}
		
		if(!StringUtils.hasLength(supplierDto.getPhoneNumber())) {
			errors.add("Veuillez renseigner un numero de telephone");
		}
		
		if(!StringUtils.hasLength(supplierDto.getAddress())) {
			errors.add("Veuillez renseigner une adresse");
		}
		
		return errors;
	}
}
