package com.refugietransaction.projections;

import java.math.BigDecimal;

public interface ByCampStockProjection {
	
	Long getProductId();
	String getNomProduit();
	String getNomProductType();
	BigDecimal getPrice();
	BigDecimal getInStockQuantity();
	String getSalesName();
}
