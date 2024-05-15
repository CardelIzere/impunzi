package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.LigneVente;
import com.refugietransaction.model.VenteStatusEnum;
import com.refugietransaction.model.Ventes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenteListDto {
	
	private Long id;
	private String nomCamp;
	private Long idNumber;
	private String saleCode;
	private String personneContact;
	private LocalDate dateVente;
	private VenteStatusEnum venteStatusEnum;
	private BigDecimal montantTotal;
	@JsonIgnore
	private MenageDto menage;
	@JsonIgnore
	private CampDto camp;
	
	@JsonIgnore
	private List<LigneVente> ligneVentes;
	
	public static VenteListDto fromEntity(Ventes ventes) {
		if(ventes == null) {
			return null;
		}
		
		List<LigneVente> ligneVentes = ventes.getLigneVentes();
		
		BigDecimal total = BigDecimal.ZERO;
		for(LigneVente ligneVente : ligneVentes) {
			BigDecimal prixUnitaire = ligneVente.getPrixUnitaire();
			total = total.add(prixUnitaire);
		}
		
		return VenteListDto.builder()
				.id(ventes.getId())
				.nomCamp(ventes.getCamp().getNomCamp())
				.idNumber(ventes.getMenage().getIdNumber())
				.saleCode(ventes.getSaleCode())
				.personneContact(ventes.getMenage().getPersonneContact())
				.dateVente(ventes.getDateVente())
				.venteStatusEnum(ventes.getVenteStatusEnum())
				.montantTotal(total)
				.build();
				
	}
	
}
