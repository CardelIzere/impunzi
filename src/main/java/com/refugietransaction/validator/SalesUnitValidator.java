package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.SalesUnitDto;

public class SalesUnitValidator {
	
	public static List<String> validate(SalesUnitDto salesUnitDto){
		List<String> errors = new ArrayList<>();
		
		if(salesUnitDto == null || !StringUtils.hasLength(salesUnitDto.getName())) {
			errors.add("Veuillez renseigner le nom de l'unité de vente");
		}
		
		return errors;
	}
}
