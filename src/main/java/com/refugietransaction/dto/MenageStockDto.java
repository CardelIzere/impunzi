package com.refugietransaction.dto;

import java.math.BigDecimal;

import com.refugietransaction.model.MvtStkMenage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenageStockDto {
	
	private ProductTypeDto productType;
	private BigDecimal inStockQuantity;

	public MenageStockDto() {
		
	}
	
	public MenageStockDto(ProductTypeDto productType, BigDecimal inStockQuantity) {
		this.productType = productType;
		this.inStockQuantity = inStockQuantity;
	}
}
