package com.refugietransaction.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.VenteStatusEnum;
import com.refugietransaction.model.Ventes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VentesDto {
	
	private Long id;
	private String saleCode;
	private LocalDate dateVente;
	private SupplierDto supplier;
	private CampDto camp;
	private MenageDto menage;
	private VenteStatusEnum venteStatusEnum;
	
	
	private List<LigneVenteDto> ligneVentes;
	
	public static VentesDto fromEntity(Ventes ventes) {
		if(ventes == null) {
			return null;
		}
		
		return VentesDto.builder()
				.id(ventes.getId())
				.saleCode(ventes.getSaleCode())
				.dateVente(ventes.getDateVente())
				.supplier(SupplierDto.fromEntity(ventes.getSupplier()))
				.camp(CampDto.fromEntity(ventes.getCamp()))
				.menage(MenageDto.fromEntity(ventes.getMenage()))
				.venteStatusEnum(ventes.getVenteStatusEnum())
				.build();
	}
	
	public static Ventes toEntity(VentesDto ventesDto) {
		if(ventesDto == null) {
			return null;
		}
		
		Ventes ventes = new Ventes();
		ventes.setId(ventesDto.getId());
		ventes.setSaleCode(ventesDto.getSaleCode());
		ventes.setDateVente(ventesDto.getDateVente());
		ventes.setSupplier(SupplierDto.toEntity(ventesDto.getSupplier()));
		ventes.setCamp(CampDto.toEntity(ventesDto.getCamp()));
		ventes.setMenage(MenageDto.toEntity(ventesDto.getMenage()));
		ventes.setVenteStatusEnum(ventesDto.getVenteStatusEnum());
		
		return ventes;
	}
}
