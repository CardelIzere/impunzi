package com.refugietransaction.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.Camp;
import com.refugietransaction.model.MvtStkSupplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampDto {
	
	private Long id;
	private String nomCamp;
	private String addressCamp;
	
	@JsonIgnore
	private List<MenageDto> menages;
	
	@JsonIgnore
	private List<MvtStkSupplier> mvtStkSuppliers;
	
	public static CampDto fromEntity(Camp camp) {
		if(camp == null) {
			return null;
		}
		
		return CampDto.builder()
				.id(camp.getId())
				.nomCamp(camp.getNomCamp())
				.addressCamp(camp.getAddressCamp())
				.build();
	}
	
	public static Camp toEntity(CampDto campDto) {
		if(campDto == null) {
			return null;
		}
		
		Camp camp = new Camp();
		camp.setId(campDto.getId());
		camp.setNomCamp(campDto.getNomCamp());
		camp.setAddressCamp(campDto.getAddressCamp());
		
		return camp;
	}
}
