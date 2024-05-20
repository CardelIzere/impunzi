package com.refugietransaction.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ProductCampStockDto {
	
	private String campName;
	private List<ProductStockQuantityDto> stockQuantities;
	
	public ProductCampStockDto() {
		
	}
	
	public void setCampName(String campName) {
		this.campName = campName;
	}
	
	public void setStockQuantities(List<ProductStockQuantityDto> stockQuantities) {
		this.stockQuantities = stockQuantities;
	}
}
