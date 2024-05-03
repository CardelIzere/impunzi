package com.refugietransaction.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.MenageDto;

public interface MenageService {
	
	MenageDto save(MenageDto dto);
	
	MenageDto findById(Long id);
	
	Page<MenageDto> findByPersonneContactNumTeleLike(String search, Pageable pageable);

	void delete(Long id);
}
