package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.CampApi;
import com.refugietransaction.dto.CampDto;
import com.refugietransaction.services.CampService;

@RestController
public class CampController implements CampApi {
	
	private final CampService campService;
	
	@Autowired
	public CampController(CampService campService) {
		this.campService = campService;
	}
	
	@Override
	public CampDto save(CampDto dto) {
		
		return campService.save(dto);
	}

	@Override
	public CampDto findById(Long idCamp) {
		return campService.findById(idCamp);
	}

	@Override
	public void delete(Long id) {
		campService.delete(id);
		
	}

	@Override
	public Page<CampDto> findAllCamps(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return campService.findByNameCampAddressLike(search, pageable);
	}

}
