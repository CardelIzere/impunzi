package com.refugietransaction.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class ProductStockQuantityDto {
	
	private String campName;
	private BigDecimal quantity;
	private String salesName;
	
	public ProductStockQuantityDto() {
		
	}
	
	public void setCampName(String campName) {
		this.campName = campName;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	
}
