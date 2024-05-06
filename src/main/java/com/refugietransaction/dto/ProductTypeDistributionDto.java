package com.refugietransaction.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTypeDistributionDto {
    CampDto campDto;
    ProductTypeDto productTypeDto;
    BigDecimal cons_moyenne_personne;

}
