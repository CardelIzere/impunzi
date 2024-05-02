package com.refugietransaction.services;

import java.util.List;

import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.model.MouvementStock;

public interface MenageService {
	
	MenageDto save(MenageDto dto);
	
	MenageDto findById(Long id);
	
	List<MenageDto> findAll();

	void delete(Long id);
}
