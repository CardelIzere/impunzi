package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import com.refugietransaction.model.MvtStkSupplier;
import com.refugietransaction.model.TypeMvtStkSupplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MvtStkSupplierDto {
	
	private Long id;
	private Instant dateMouvement;
	private BigDecimal quantite;
	private TypeMvtStkSupplier typeMouvement;
	private SupplierDto supplier;
	private ProductDto produit;
	private CampDto camp;
	
	
	public static MvtStkSupplierDto fromEntity(MvtStkSupplier mouvementStockSupplier) {
		if(mouvementStockSupplier == null) {
			return null;
			//TODO throw an exception
		}
		
		return MvtStkSupplierDto.builder()
				.id(mouvementStockSupplier.getId())
				.dateMouvement(mouvementStockSupplier.getDateMouvement())
				.quantite(mouvementStockSupplier.getQuantite())
				.typeMouvement(mouvementStockSupplier.getTypeMouvement())
				.supplier(SupplierDto.fromEntity(mouvementStockSupplier.getSupplier()))
				.produit(ProductDto.fromEntity(mouvementStockSupplier.getProduit()))
				.camp(CampDto.fromEntity(mouvementStockSupplier.getCamp()))
				.build();
	}
	
	public static MvtStkSupplier toEntity(MvtStkSupplierDto mouvementStockSupplierDto) {
		if(mouvementStockSupplierDto == null) {
			return null;
			//TODO throw an exception
		}
		
		MvtStkSupplier mouvementStockSupplier = new MvtStkSupplier();
		mouvementStockSupplier.setId(mouvementStockSupplierDto.getId());
		mouvementStockSupplier.setDateMouvement(mouvementStockSupplierDto.getDateMouvement());
		mouvementStockSupplier.setQuantite(mouvementStockSupplierDto.getQuantite());
		mouvementStockSupplier.setTypeMouvement(mouvementStockSupplierDto.getTypeMouvement());
		mouvementStockSupplier.setSupplier(SupplierDto.toEntity(mouvementStockSupplierDto.getSupplier()));
		mouvementStockSupplier.setProduit(ProductDto.toEntity(mouvementStockSupplierDto.getProduit()));
		mouvementStockSupplier.setCamp(CampDto.toEntity(mouvementStockSupplierDto.getCamp()));
		
		return mouvementStockSupplier;
	}
}
