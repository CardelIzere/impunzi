package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.refugietransaction.model.TypeDistributionEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTypeDistributionDto {
	TypeDistributionEnum typeDistributionEnum;
    CampDto campDto;
    ProductTypeDto productTypeDto;
    BigDecimal cons_moyenne_personne;

}
