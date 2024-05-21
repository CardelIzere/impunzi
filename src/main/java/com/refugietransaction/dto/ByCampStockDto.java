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
	private String nomProductType;
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
				.nomProductType(byCampStockProjection.getNomProductType())
				.price(byCampStockProjection.getPrice())
				.inStockQuantity(byCampStockProjection.getInStockQuantity())
				.salesName(byCampStockProjection.getSalesName())
				.build();
	}

	
}
