package com.refugietransaction.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.Camp;
import com.refugietransaction.model.Menage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenageDto {
	
	private Long id;
	private Long idNumber;
	private String personneContact;
	private String numTelephone;
	private String langueParlee;
	private Integer nombrePersonnes;
	@JsonIgnore
	private CampDto camp;
	
	@JsonIgnore
	private List<MouvementStockDto> mouvementStocks;
	
	public static MenageDto fromEntity(Menage menage) {
		if(menage == null) {
			return null;
			//TODO throw an exception
		}
		
		return MenageDto.builder()
				.id(menage.getId())
				.idNumber(menage.getIdNumber())
				.personneContact(menage.getPersonneContact())
				.numTelephone(menage.getNumTelephone())
				.langueParlee(menage.getLangueParlee())
				.nombrePersonnes(menage.getNombrePersonnes())
				.camp(CampDto.fromEntity(menage.getCamp()))
				.build();
	}
	
	public static Menage toEntity(MenageDto menageDto) {
		if(menageDto == null) {
			return null;
			//TODO throw an exception
		}
		
		Menage menage = new Menage();
		menage.setId(menageDto.getId());
		menage.setIdNumber(menageDto.getIdNumber());
		menage.setPersonneContact(menageDto.getPersonneContact());
		menage.setNumTelephone(menageDto.getNumTelephone());
		menage.setLangueParlee(menageDto.getLangueParlee());
		menage.setNombrePersonnes(menageDto.getNombrePersonnes());
		menage.setCamp(CampDto.toEntity(menageDto.getCamp()));
		
		return menage;
	}
	
}
