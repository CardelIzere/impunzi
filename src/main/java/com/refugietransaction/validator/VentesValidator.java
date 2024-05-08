package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import com.refugietransaction.dto.VentesDto;

public class VentesValidator {
	
	public static List<String> validate(VentesDto dto) {
	    List<String> errors = new ArrayList<>();
	    if (dto == null) {
	      errors.add("Veuillez selectionner une menage");
	      return errors;
	    }

	    if (dto.getMenage() == null || dto.getMenage().getId() == null || dto.getMenage().getId().compareTo(0L) == 0) {
	      errors.add("Veuillez selectionner une menage");
	    }

	    return errors;
	  }
}
