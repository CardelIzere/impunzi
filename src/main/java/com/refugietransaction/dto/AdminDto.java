package com.refugietransaction.dto;

import com.refugietransaction.model.Admin;
import com.refugietransaction.model.AdminTypeEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDto {
	
	private Long id;
	private UserDto user;
	private AdminTypeEnum adminTypeEnum;
	private SupplierDto supplier;
	
	public static AdminDto fromEntity(Admin admin) {
		if(admin == null) {
			return null;
		}
		
		return AdminDto.builder()
				.id(admin.getId())
				.user(UserDto.fromEntity(admin.getUser()))
				.adminTypeEnum(admin.getAdminTypeEnum())
				.build();
	}
	
	public static Admin toEntity(AdminDto adminDto) {
		if(adminDto == null) {
			return null;
		}
		
		Admin admin = new Admin();
		admin.setId(adminDto.getId());
		admin.setUser(UserDto.toEntity(adminDto.getUser()));
		admin.setAdminTypeEnum(adminDto.getAdminTypeEnum());
		
		return admin;
	}
}
