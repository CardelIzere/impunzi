package com.refugietransaction.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ByCampStockDto {
	
	private ProductDto product;
	private BigDecimal inStockQuantity;

	public ByCampStockDto() {
		
	}
	
	public ByCampStockDto(ProductDto product, BigDecimal inStockQuantity) {
		this.product = product;
		this.inStockQuantity = inStockQuantity;
	}
}
