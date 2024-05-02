package com.refugietransaction.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.Admin;
import com.refugietransaction.model.Magasinier;
import com.refugietransaction.model.Supplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
	
	private Long id;
	private String name;
	private String phoneNumber;
	private String address;
	
	@JsonIgnore
	private List<Admin> admins;
	@JsonIgnore
	private List<Magasinier> magasiniers;
	
	public static SupplierDto fromEntity(Supplier supplier) {
		if(supplier == null) {
			return null;
		}
		
		return SupplierDto.builder()
				.id(supplier.getId())
				.name(supplier.getName())
				.phoneNumber(supplier.getPhoneNumber())
				.address(supplier.getAddress())
				.build();
	}
	
	public static Supplier toEntity(SupplierDto supplierDto) {
		if(supplierDto == null) {
			return null;
		}
		
		Supplier supplier = new Supplier();
		supplier.setId(supplierDto.getId());
		supplier.setName(supplierDto.getName());
		supplier.setPhoneNumber(supplierDto.getPhoneNumber());
		supplier.setAddress(supplierDto.getAddress());
		
		return supplier;
	}
}
