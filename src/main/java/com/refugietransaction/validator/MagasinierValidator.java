package com.refugietransaction.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.MagasinierDto;

public class MagasinierValidator {
	
	public static List<String> validate(MagasinierDto magasinierDto){
		List<String> errors = new ArrayList<>();

		if(magasinierDto == null) {
			errors.add("Veuillez renseigner le nom complet de l'utilisateur");
			errors.add("Veuillez renseigner l'email de l'utilisateur");
			errors.add("Veuillez renseigner le numero de telephone");
			errors.add("Veuillez renseigner un fournisseur");
			errors.add("Veuillez renseigner un camp");
			return errors;
		}

		if (!StringUtils.hasLength(magasinierDto.getUser().getUserFullName())){
			errors.add("Veuillez renseigner le nom d'utilisateur");
		}

		if (!StringUtils.hasLength(magasinierDto.getUser().getUserEmail())){
			errors.add("Veuillez renseigner l'email de l'utilisateur");
		}
		if (!isValidEmail(magasinierDto.getUser().getUserEmail())){
			errors.add("Email de l'utilisateur invalide");
		}
		if (!StringUtils.hasLength(magasinierDto.getUser().getUserPhoneNumber())){
			errors.add("Veuillez renseigner le numero de telephone");
		}

		if(magasinierDto.getSupplier() == null || magasinierDto.getSupplier().getId() == null || magasinierDto.getSupplier().getId().compareTo(0L) == 0) {
			errors.add("Entreprise ne peut pas etre nul");
		}
		if(magasinierDto.getCamp() == null || magasinierDto.getCamp().getId() == null || magasinierDto.getCamp().getId().compareTo(0L) == 0) {
			errors.add("Place de parking ne peut pas etre nul");
		}

		return errors;
	}

	private static boolean isValidEmail(String email) {

		String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
