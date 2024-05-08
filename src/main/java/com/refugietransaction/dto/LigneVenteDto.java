package com.refugietransaction.dto;

import java.math.BigDecimal;

import com.refugietransaction.model.LigneVente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneVenteDto {
	
	private Long id;
	private VentesDto vente;
	private ProductDto product;
	private BigDecimal quantite;
	private BigDecimal prixUnitaire;
	
	
	public static LigneVenteDto fromEntity(LigneVente ligneVente) {
		if(ligneVente == null) {
			return null;
		}
		
		return LigneVenteDto.builder()
				.id(ligneVente.getId())
				.vente(VentesDto.fromEntity(ligneVente.getVente()))
				.product(ProductDto.fromEntity(ligneVente.getProduct()))
				.quantite(ligneVente.getQuantite())
				.prixUnitaire(ligneVente.getPrixUnitaire())
				.build();
	}
	
	public static LigneVente toEntity(LigneVenteDto ligneVenteDto) {
		if(ligneVenteDto == null) {
			return null;
		}
		
		LigneVente ligneVente = new LigneVente();
		ligneVente.setId(ligneVenteDto.getId());
		ligneVente.setVente(VentesDto.toEntity(ligneVenteDto.getVente()));
		ligneVente.setProduct(ProductDto.toEntity(ligneVenteDto.getProduct()));
		ligneVente.setQuantite(ligneVenteDto.getQuantite());
		ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
		
		return ligneVente;
	}
}
