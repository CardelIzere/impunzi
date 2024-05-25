package com.refugietransaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDto {
	
	private Long menageCount;
	private Long supplierCount;
	private Long campCount;
	
	public DashboardDto() {
		
	}
	
	public DashboardDto(Long menageCount, Long supplierCount, Long campCount) {
		this.menageCount = menageCount;
		this.supplierCount = supplierCount;
		this.campCount = campCount;
	}
}
