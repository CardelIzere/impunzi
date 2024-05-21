package com.refugietransaction.projections;

import java.math.BigDecimal;

public interface ByCampStockProjection {
	
	Long getProductId();
	String getNomProduit();
	BigDecimal getPrice();
	BigDecimal getInStockQuantity();
	String getSalesName();
}
