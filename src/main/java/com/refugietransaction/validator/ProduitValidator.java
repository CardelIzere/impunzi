package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.ProduitDto;

public class ProduitValidator {
	public static List<String> validate(ProduitDto produitDto){
		List<String> errors = new ArrayList<>();
		
		if(produitDto == null || !StringUtils.hasLength(produitDto.getNomProduit())) {
			errors.add("Veuillez renseigner le nom du produit");
		}
		
		return errors;
		
	}
}
