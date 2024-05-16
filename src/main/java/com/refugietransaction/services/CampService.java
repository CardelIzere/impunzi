package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.CampDto;

public interface CampService {
	
	CampDto save(CampDto dto);
	
	CampDto findById(Long id);
	
	List<CampDto> findAll();
	
	Page<CampDto> findByNameCampAddressLike(String search, Pageable pageable);
	
	//Page<CampDto> findByCampSupplierAndSearch(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	void delete(Long id);
}
