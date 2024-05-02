package com.refugietransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.SuperadminApi;
import com.refugietransaction.dto.SuperadminDto;
import com.refugietransaction.services.SuperadminService;

@RestController
public class SuperadminController implements SuperadminApi {
	
	private final SuperadminService superadminService;

	@Autowired
	public SuperadminController(SuperadminService superadminService) {
		this.superadminService = superadminService;
	}

	@Override
	public SuperadminDto save(SuperadminDto dto) {
		
		return superadminService.save(dto);
	}

	@Override
	public SuperadminDto findById(Long id) {
		
		return superadminService.findById(id);
	}

	@Override
	public Page<SuperadminDto> findByNameEmailPhoneLike(String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return superadminService.findByNameEmailPhoneLike(search,pageable);
	}

	@Override
	public void delete(Long id) {
		superadminService.delete(id);
		
	}
}
