package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.AdminApi;
import com.refugietransaction.dto.AdminDto;
import com.refugietransaction.services.AdminService;

@RestController
public class AdminController implements AdminApi {
	
	private final AdminService adminService;
	
	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@Override
	public AdminDto save(AdminDto dto) {
		
		return adminService.save(dto);
	}

	@Override
	public AdminDto findById(Long id) {
		
		return adminService.findById(id);
	}

	@Override
	public Page<AdminDto> findMainAdmins(String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return adminService.findAllMainAdmins(search,pageable);
	}

	@Override
	public Page<AdminDto> findSupplierAdmins(Long idSupplier, String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return adminService.findSupplierAdmins(idSupplier,search,pageable);
	}

	@Override
	public void delete(Long id) {
		adminService.delete(id);
		
	}

	@Override
	public Page<AdminDto> findAllAdmins(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return adminService.findAllAdmins(pageable);
	}
}
