package com.refugietransaction.services;

import java.util.List;

import com.refugietransaction.dto.CampDto;

public interface CampService {
	
	CampDto save(CampDto dto);
	
	CampDto findById(Long id);
	
	List<CampDto> findAll();
	
	void delete(Long id);
}
