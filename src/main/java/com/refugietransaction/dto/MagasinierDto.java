package com.refugietransaction.dto;

import com.refugietransaction.model.Magasinier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MagasinierDto {
	
	private Long id;
	private UserDto user;
	private SupplierDto supplier;
	private CampDto camp;

	public static MagasinierDto fromEntity(Magasinier magasinier) {
		if(magasinier == null) {
			return null;
		}

		return MagasinierDto.builder()
				.id(magasinier.getId())
				.user(UserDto.fromEntity(magasinier.getUser()))
				.supplier(SupplierDto.fromEntity(magasinier.getSupplier()))
				.camp(CampDto.fromEntity(magasinier.getCamp()))
				.build();
	}

	public static Magasinier toEntity(MagasinierDto magasinierDto) {
		if(magasinierDto == null) {
			return null;
		}

		Magasinier magasinier = new Magasinier();
		magasinier.setId(magasinierDto.getId());
		magasinier.setUser(UserDto.toEntity(magasinierDto.getUser()));
		magasinier.setSupplier(SupplierDto.toEntity(magasinierDto.getSupplier()));
		magasinier.setCamp(CampDto.toEntity(magasinierDto.getCamp()));

		return magasinier;
		
	}
}
