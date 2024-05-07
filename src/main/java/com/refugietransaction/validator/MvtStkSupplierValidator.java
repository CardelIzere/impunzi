package com.refugietransaction.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.MvtStkSupplierDto;

public class MvtStkSupplierValidator {
	public static List<String> validate(MvtStkSupplierDto mouvementStockSupplierDto){
		List<String> errors = new ArrayList<>();
		
		if (mouvementStockSupplierDto == null){

			errors.add("Veillez renseigner la date du mouvement");
			errors.add("Veillez renseigner la quantite du mouvenent");
			errors.add("Veillez selectionner le type mouvement");
			errors.add("Veillez selectionner un produit");
			errors.add("Veillez selectionner un fournisseur");
			errors.add("Veuillez selectionner un camp");

		}
		
		if(mouvementStockSupplierDto.getDateMouvement() == null) {
			errors.add("Veillez renseigner la date du mouvement");
		}
		if(mouvementStockSupplierDto.getQuantite() == null || mouvementStockSupplierDto.getQuantite().compareTo(BigDecimal.ZERO) == 0) {
			errors.add("Veillez renseigner la quantite du mouvenent");
		}
		if(!StringUtils.hasLength(mouvementStockSupplierDto.getTypeMouvement().name())) {
			errors.add("Veillez selectionner le type mouvement");
		}
		if(mouvementStockSupplierDto.getProduit() == null || mouvementStockSupplierDto.getProduit().getId() == null) {
			errors.add("Veillez selectionner un produit");
		}
		if(mouvementStockSupplierDto.getSupplier() == null || mouvementStockSupplierDto.getSupplier().getId() == null) {
			errors.add("Veillez selectionner un fournisseur");
		}
		if(mouvementStockSupplierDto.getCamp() == null || mouvementStockSupplierDto.getCamp().getId() == null) {
			errors.add("Veuillez selectionner un camp");
		}
		return errors;
	}
}
