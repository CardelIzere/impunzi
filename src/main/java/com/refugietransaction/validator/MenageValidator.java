package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.MenageDto;

public class MenageValidator {
	public static List<String> validate(MenageDto menageDto){
		List<String> errors = new ArrayList<>();
		
		if (menageDto == null) {
		    errors.add("Veuillez renseigner une personne de contact");
		    errors.add("Veuillez renseigner un numero de telephone");
		    errors.add("Veuillez renseigner une langue parlee");
		    errors.add("Veuillez renseigner le nombre de personnes dans le menage");
		    return errors;
		}
		
		if(!StringUtils.hasLength(menageDto.getPersonneContact())) {
			errors.add("Veuillez renseigner une personne de contact");
		}
		
		if(!StringUtils.hasLength(menageDto.getNumTelephone())) {
			errors.add("Veuillez renseigner le numero de telephone");
		}
		
		if(!StringUtils.hasLength(menageDto.getLangueParlee())) {
			errors.add("Veuillez renseigner la langue parlee");
		}
		
		if(menageDto.getNombrePersonnes() == null || menageDto.getNombrePersonnes().compareTo(0) == 0) {
			errors.add("Veuillez renseigner le nombre de personnes dans le menage");
		}
		
		return errors;
	}
}
