package com.refugietransaction.services;

import java.math.BigDecimal;
import java.util.List;

import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.ProductTypeDistributionDto;

public interface MvtStkMenageService {
	
	void productTypeDistribution(ProductTypeDistributionDto productTypeDistributionDto);
	
	BigDecimal stockReelMenage(Long idProductType, Long idMenage);
	
	List<MvtStkMenageDto> mvtStkProductTypeMenage(Long idProductType, Long idMenage);
}
