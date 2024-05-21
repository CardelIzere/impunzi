package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;

@Getter
public class ProductCampStockDto {
	
	private String campName;
	private BigDecimal quantity;
	private String salesName;
	
	public ProductCampStockDto() {
		
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
