package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.refugietransaction.model.MouvementStock;
import com.refugietransaction.model.TypeMouvementStock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MouvementStockDto {
	
	private Long id;
	private Date dateMouvement;
	private BigDecimal quantite;
	private TypeMouvementStock typeMouvement;
	private MenageDto menage;
	private ProductDto produit;
	
	public static MouvementStockDto fromEntity(MouvementStock mouvementStock) {
		if(mouvementStock == null) {
			return null;
			//TODO throw an exception
		}
		
		return MouvementStockDto.builder()
				.id(mouvementStock.getId())
				.dateMouvement(mouvementStock.getDateMouvement())
				.quantite(mouvementStock.getQuantite())
				.typeMouvement(mouvementStock.getTypeMouvement())
				.menage(MenageDto.fromEntity(mouvementStock.getMenage()))
				.produit(ProductDto.fromEntity(mouvementStock.getProduit()))
				.build();
	}
	
	public static MouvementStock toEntity(MouvementStockDto mouvementStockDto) {
		if(mouvementStockDto == null) {
			return null;
			//TODO throw an exception
		}
		
		MouvementStock mouvementStock = new MouvementStock();
		mouvementStock.setId(mouvementStockDto.getId());
		mouvementStock.setDateMouvement(mouvementStockDto.getDateMouvement());
		mouvementStock.setQuantite(mouvementStockDto.getQuantite());
		mouvementStock.setTypeMouvement(mouvementStockDto.getTypeMouvement());
		mouvementStock.setMenage(MenageDto.toEntity(mouvementStockDto.getMenage()));
		mouvementStock.setProduit(ProductDto.toEntity(mouvementStockDto.getProduit()));
		
		return mouvementStock;
	}
}
