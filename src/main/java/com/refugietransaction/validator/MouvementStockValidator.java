package com.refugietransaction.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.MouvementStockDto;

public class MouvementStockValidator {
	public static List<String> validate(MouvementStockDto mouvementStockDto){
		List<String> errors = new ArrayList<>();
		
		if (mouvementStockDto == null){

			errors.add("Veillez renseigner la date du mouvement");
			errors.add("Veillez renseigner la quantite du mouvenent");
			errors.add("Veillez selectionner le type mouvement");
			errors.add("Veillez selectionner un produit");
			errors.add("Veillez selectionner une menage");

		}
		
		if(mouvementStockDto.getDateMouvement() == null) {
			errors.add("Veillez renseigner la date du mouvement");
		}
		if(mouvementStockDto.getQuantite() == null || mouvementStockDto.getQuantite().compareTo(BigDecimal.ZERO) == 0) {
			errors.add("Veillez renseigner la quantite du mouvenent");
		}
		if(!StringUtils.hasLength(mouvementStockDto.getTypeMouvement().name())) {
			errors.add("Veillez selectionner le type mouvement");
		}
		if(mouvementStockDto.getProduit() == null || mouvementStockDto.getProduit().getId() == null) {
			errors.add("Veillez selectionner un produit");
		}
		if(mouvementStockDto.getMenage() == null || mouvementStockDto.getMenage().getId() == null) {
			errors.add("Veillez selectionner une menage");
		}
		return errors;
	}
}
