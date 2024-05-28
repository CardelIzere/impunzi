package com.refugietransaction.dto;

import java.math.BigDecimal;

import com.refugietransaction.projections.ByCampStockProjection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ByCampStockDto {
	
	private Long productId;
	private String nomProduct;
	private ProductTypeDto productTypeDto;
	private SupplierDto supplierDto;
	private BigDecimal price;
	private BigDecimal inStockQuantity;
	private String salesName;
	
	public static ByCampStockDto fromEntity(ByCampStockProjection byCampStockProjection) {
		if(byCampStockProjection == null) {
			return null;
		}
		
		return ByCampStockDto.builder()
				.productId(byCampStockProjection.getProductId())
				.nomProduct(byCampStockProjection.getNomProduit())
				.productTypeDto(ProductTypeDto.fromEntity(byCampStockProjection.getProductTypeDto()))
				.supplierDto(SupplierDto.fromEntity(byCampStockProjection.getSupplierDto()))
				.price(byCampStockProjection.getPrice())
				.inStockQuantity(byCampStockProjection.getInStockQuantity())
				.salesName(byCampStockProjection.getSalesName())
				.build();
	}

	
}
