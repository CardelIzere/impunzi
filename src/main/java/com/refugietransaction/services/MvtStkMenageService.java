package com.refugietransaction.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.MenageStockDto;
import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.ProductTypeDistributionDto;

public interface MvtStkMenageService {
	
	void productTypeDistribution(ProductTypeDistributionDto productTypeDistributionDto);
	
	MvtStkMenageDto sortie(MvtStkMenageDto dto);
	
	BigDecimal stockReelMenage(Long idProductType, Long idMenage);
	
	List<MvtStkMenageDto> mvtStkProductTypeMenage(Long idProductType, Long idMenage);
	
	Page<MvtStkMenageDto> findEntriesByProductTypeMenageLike(String search, Pageable pageable);
	
	List<MvtStkMenageDto> findMvtStkMenageByMenageId(Long idMenage);
	
	Page<MvtStkMenageDto> findAllByProductTypeMenageLike(String search, Pageable pageable);
	
	List<MenageStockDto> getTotalQuantityByIdMenage(Long idMenage);
	
	void delete(Long id);
}
