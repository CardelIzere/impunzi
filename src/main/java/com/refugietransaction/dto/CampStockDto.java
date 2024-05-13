package com.refugietransaction.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class CampStockDto {
	
	private String campName;
	private List<StockQuantityDto> stockQuantities;
	
	public CampStockDto() {
		
	}
	
	public void setCampName(String campName) {
		this.campName = campName;
	}
	
	public void setStockQuantities(List<StockQuantityDto> stockQuantities) {
		this.stockQuantities = stockQuantities;
	}
}
