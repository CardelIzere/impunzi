package com.refugietransaction.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.MenageDto;

public interface MenageService {
	
	MenageDto save(MenageDto dto);
	
	MenageDto findById(Long id);
	
	Page<MenageDto> findByPersonneContactNumTeleLike(String search, Pageable pageable);
	
	List<MenageDto> findAllMenagesByCampId(Long campId);
	
	List<MenageDto> findAllByCampIdByPersonneContactNumTeleIdNumberLike(Long idCamp, String search);

	void delete(Long id);
}
