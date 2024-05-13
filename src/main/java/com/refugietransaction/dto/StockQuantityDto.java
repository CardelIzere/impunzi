package com.refugietransaction.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class StockQuantityDto {
	
	private String productName;
	private BigDecimal quantity;
	
	public StockQuantityDto() {
		
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
}
