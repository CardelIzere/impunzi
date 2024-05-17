package com.refugietransaction.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class StockQuantityDto {
	
	private String productName;
	private BigDecimal quantity;
	private String salesName;
	
	public StockQuantityDto() {
		
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
}
