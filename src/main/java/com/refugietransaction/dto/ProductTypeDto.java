package com.refugietransaction.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.ProductType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTypeDto {
	
	private Long id;
	private String nameProductType;
	
	@JsonIgnore
	private List<ProductDto> products;
	
	public static ProductTypeDto fromEntity(ProductType productType) {
		if(productType == null) {
			return null;
		}
		
		return ProductTypeDto.builder()
				.id(productType.getId())
				.nameProductType(productType.getNameProductTpe())
				.build();
	}
	
	public static ProductType toEntity(ProductTypeDto productTypeDto) {
		if(productTypeDto == null) {
			return null;
		}
		
		ProductType productType = new ProductType();
		productType.setId(productTypeDto.getId());
		productType.setNameProductTpe(productTypeDto.getNameProductType());
		
		return productType;
	}
}