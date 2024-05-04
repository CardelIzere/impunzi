package com.refugietransaction.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.model.SalesUnit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesUnitDto {
	
	private Long id;
	private String name;
	
	@JsonIgnore
	private List<ProductType> productTypes;
	
	public static SalesUnitDto fromEntity(SalesUnit salesUnit) {
		if(salesUnit == null) {
			return null;
		}
		
		return SalesUnitDto.builder()
				.id(salesUnit.getId())
				.name(salesUnit.getName())
				.build();
	}
	
	public static SalesUnit toEntity(SalesUnitDto salesUnitDto) {
		if(salesUnitDto == null) {
			return null;
		}
		
		SalesUnit salesUnit = new SalesUnit();
		salesUnit.setId(salesUnitDto.getId());
		salesUnit.setName(salesUnitDto.getName());
		
		return salesUnit;
	}
}
