package com.refugietransaction.services;

import java.util.List;

import com.refugietransaction.dto.ProduitDto;

public interface ProduitService {
	
	ProduitDto save(ProduitDto dto);
	
	ProduitDto findById(Long id);
	
	List<ProduitDto> findAll();
	
	void delete(Long id);
}
