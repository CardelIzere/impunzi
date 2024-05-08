package com.refugietransaction.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.MvtStkMenageDto;

public class MvtStkMenageValidator {
	public static List<String> validate(MvtStkMenageDto mvtStkMenageDto){
		List<String> errors = new ArrayList<>();
		
		if(mvtStkMenageDto == null) {
			errors.add("Veuillez selectionner le type de produit");
		}
		
		if(mvtStkMenageDto.getProductType() == null || mvtStkMenageDto.getProductType().getId() == null) {
			errors.add("Veuillez selectionner le type de produit");
		}
		
		return errors;
	}
}
