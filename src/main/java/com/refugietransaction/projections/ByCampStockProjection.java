package com.refugietransaction.projections;

import java.math.BigDecimal;

import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.model.Supplier;

public interface ByCampStockProjection {
	
	Long getProductId();
	String getNomProduit();
	ProductType getProductTypeDto();
	Supplier getSupplierDto();
	BigDecimal getPrice();
	BigDecimal getInStockQuantity();
	String getSalesName();
}
