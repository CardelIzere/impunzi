package com.refugietransaction.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.SuperadminDto;

public interface SuperadminService {
	
	SuperadminDto save(SuperadminDto dto);
	
	SuperadminDto findById(Long id);

	Page<SuperadminDto> findByNameEmailPhoneLike(String search, Pageable pageable);
	
	void delete(Long id);
}
