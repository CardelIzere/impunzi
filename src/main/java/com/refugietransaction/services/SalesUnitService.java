package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.SalesUnitDto;


public interface SalesUnitService {
	
	SalesUnitDto save(SalesUnitDto dto);
	
	SalesUnitDto findById(Long id);
	
	List<SalesUnitDto> findAll();
	
	Page<SalesUnitDto> findByNameLike(String search, Pageable pageable);
	
	void delete(Long id);
}
