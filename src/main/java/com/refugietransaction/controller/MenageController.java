package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.MenageApi;
import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.services.MenageService;

@RestController

public class MenageController implements MenageApi {
	
	private final MenageService menageService;
	
	@Autowired
	public MenageController(MenageService menageService) {
		this.menageService = menageService;
	}

	@Override
	public MenageDto save(MenageDto dto) {
		return menageService.save(dto);
	}

	@Override
	public MenageDto findById(Long idMenage) {
		return menageService.findById(idMenage);
	}

	@Override
	public List<MenageDto> findAll() {
		return menageService.findAll();
	}

	@Override
	public void delete(Long id) {
		menageService.delete(id);
		
	}

}
