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
	private String name;
	private SalesUnitDto salesUnit;
	
	@JsonIgnore
	private List<ProductDto> products;
	
	@JsonIgnore
	private List<MvtStkMenageDto> mvtStkMenages;
	
	public static ProductTypeDto fromEntity(ProductType productType) {
		if(productType == null) {
			return null;
		}
		
		return ProductTypeDto.builder()
				.id(productType.getId())
				.name(productType.getName())
				.salesUnit(SalesUnitDto.fromEntity(productType.getSalesUnit()))
				.build();
	}
	
	public static ProductType toEntity(ProductTypeDto productTypeDto) {
		if(productTypeDto == null) {
			return null;
		}
		
		ProductType productType = new ProductType();
		productType.setId(productTypeDto.getId());
		productType.setName(productTypeDto.getName());
		productType.setSalesUnit(SalesUnitDto.toEntity(productTypeDto.getSalesUnit()));
		
		return productType;
	}
}
