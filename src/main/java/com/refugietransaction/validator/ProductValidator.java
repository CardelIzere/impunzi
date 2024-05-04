package com.refugietransaction.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.refugietransaction.dto.ProductDto;

public class ProductValidator {
	public static List<String> validate(ProductDto productDto){
		List<String> errors = new ArrayList<>();
		
		if(productDto == null) {
			errors.add("Veuillez renseigner le nom du produit");
			errors.add("Veuillez renseigner le prix");
			errors.add("Veuillez renseigner le poids");
			errors.add("Veuillez selectionner le type de produit");
		}
		if(!StringUtils.hasLength(productDto.getNomProduit())) {
			errors.add("Veuillez renseigner le nom du produit");
		}
		
		if(productDto.getPrice() == null) {
			errors.add("Veuillez renseigner un prix");
		}
		
		if(productDto.getPrice() == null || productDto.getPrice().compareTo(BigDecimal.valueOf(0)) == 0) {
			errors.add("Le prix doit etre superieur à zero");
		}
		
		if(!StringUtils.hasLength(productDto.getPoids())) {
			errors.add("Veuillez renseigner le poids");
		}
		
		if(productDto.getProductType() == null || productDto.getProductType().getId() == null) {
			errors.add("Veuillez selectionner le type de produit");
		}
		
		
		return errors;
		
	}
}
