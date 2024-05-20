package com.refugietransaction.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class ProductStockQuantityDto {
	
	private BigDecimal quantity;
	private String salesName;
	
	public ProductStockQuantityDto() {
		
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	
}
