package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.MenageApi;
import com.refugietransaction.dto.MenageDto;
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
	public void delete(Long id) {
		menageService.delete(id);
		
	}

	@Override
	public Page<MenageDto> findAllMenages(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return menageService.findByPersonneContactNumTeleLike(search, pageable);
	}

	@Override
	public List<MenageDto> findAllMenagesByCampId(Long idCamp) {
		
		return menageService.findAllMenagesByCampId(idCamp);
	}

	@Override
	public List<MenageDto> findAllByCampIdByPersoContactNumTeleIdNumberLike(Long idCamp, String search) {
		
		return menageService.findAllByCampIdByPersonneContactNumTeleIdNumberLike(idCamp, search);
	}

}
