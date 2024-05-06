package com.refugietransaction.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.refugietransaction.dto.ProductTypeDistributionDto;

public class ProductTypeDistributionValidator {
	
	public static List<String> validate(ProductTypeDistributionDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Veuillez sélectionner un camp");
            errors.add("Veuillez sélectionner un type de produit");
            errors.add("Veuillez renseigner la consommation moyenne par personne");

            return errors;
        }
        if (dto.getCampDto() == null || dto.getCampDto().getId() == null || dto.getCampDto().getId().compareTo(0L) == 0) {
            errors.add("Veuillez sélectionner un camp");
        }
        if (dto.getProductTypeDto() == null || dto.getProductTypeDto().getId() == null || dto.getProductTypeDto().getId().compareTo(0L) == 0) {
            errors.add("Veuillez sélectionner un type de produit");
        }

        if(dto.getCons_moyenne_personne() == null || dto.getCons_moyenne_personne().compareTo(BigDecimal.valueOf(0)) == 0) {
            errors.add("Veuillez renseigner la consommation moyenne par personne");
        }

        return errors;
    }

}
