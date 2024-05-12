package com.refugietransaction.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierStockDto {
	
	private CampDto camp;
	private ProductDto product;
	private BigDecimal inStockQuantity;
	
	public SupplierStockDto() {
		
	}
	
	public SupplierStockDto(CampDto camp, ProductDto product, BigDecimal inStockQuantity) {
		this.camp = camp;
		this.product = product;
		this.inStockQuantity = inStockQuantity;
	}
}
