package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.CampDto;


public class CampValidator {
	
	public static List<String> validate(CampDto campDto){
		List<String> errors = new ArrayList<>();
		
		if(campDto == null || !StringUtils.hasLength(campDto.getNomCamp())){
			errors.add("Veuillez renseigner le nom du camp");
		}
		return errors;
	}
}
