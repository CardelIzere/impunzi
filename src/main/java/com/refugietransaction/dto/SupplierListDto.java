package com.refugietransaction.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.Admin;
import com.refugietransaction.model.AdminTypeEnum;
import com.refugietransaction.model.Magasinier;
import com.refugietransaction.model.Supplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierListDto {
	
	private Long id;
	private String name;
	private String phoneNumber;
	private String address;
	private String adminName;
	private Boolean isSupplierActive;
	
	@JsonIgnore
	private List<Admin> admins;
	@JsonIgnore
	private List<Magasinier> magasiniers;
	
	public static SupplierListDto fromEntity(Supplier supplier) {
		if(supplier == null) {
			return null;
		}
		
		List<Admin> admins = supplier.getAdmins();
		String toto = "";
		for(Admin admin : admins) {
			if(admin.getAdminTypeEnum().equals(AdminTypeEnum.MAIN_ADMIN)) {
				toto = admin.getUser().getUserFullName();
			}
		}
		
		return SupplierListDto.builder()
				.id(supplier.getId())
				.name(supplier.getName())
				.phoneNumber(supplier.getPhoneNumber())
				.address(supplier.getAddress())
				.adminName(toto)
				.isSupplierActive(supplier.getIsSupplierActive())
				.build();
				
	}
	
}
