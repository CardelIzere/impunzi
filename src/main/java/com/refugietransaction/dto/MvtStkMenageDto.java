package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.refugietransaction.model.MvtStkMenage;
import com.refugietransaction.model.TypeMvtStkMenageEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MvtStkMenageDto {
	
	private Long id;
	private LocalDate dateMvt;
	private BigDecimal quantite;
	private TypeMvtStkMenageEnum typeMvtStkMenage;
	private MenageDto menage;
	private ProductTypeDto productType;
	
	public static MvtStkMenageDto fromEntity(MvtStkMenage mvtStkMenage) {
		if(mvtStkMenage == null) {
			return null;
		}
		
		return MvtStkMenageDto.builder()
				.id(mvtStkMenage.getId())
				.dateMvt(mvtStkMenage.getDateMvt())
				.quantite(mvtStkMenage.getQuantite())
				.typeMvtStkMenage(mvtStkMenage.getTypeMvtStkMenageEnum())
				.menage(MenageDto.fromEntity(mvtStkMenage.getMenage()))
				.productType(ProductTypeDto.fromEntity(mvtStkMenage.getProductType()))
				.build();
	}
	
	public static MvtStkMenage toEntity(MvtStkMenageDto mvtStkMenageDto) {
		if(mvtStkMenageDto == null) {
			return null;
		}
		
		MvtStkMenage mvtStkMenage = new MvtStkMenage();
		mvtStkMenage.setId(mvtStkMenageDto.getId());
		mvtStkMenage.setDateMvt(mvtStkMenageDto.getDateMvt());
		mvtStkMenage.setQuantite(mvtStkMenageDto.getQuantite());
		mvtStkMenage.setTypeMvtStkMenageEnum(mvtStkMenageDto.getTypeMvtStkMenage());
		mvtStkMenage.setMenage(MenageDto.toEntity(mvtStkMenageDto.getMenage()));
		mvtStkMenage.setProductType(ProductTypeDto.toEntity(mvtStkMenageDto.getProductType()));
		
		return mvtStkMenage;
	}
}
