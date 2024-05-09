package com.refugietransaction.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.MvtStkMenageApi;
import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.ProductTypeDistributionDto;
import com.refugietransaction.services.MvtStkMenageService;

@RestController
public class MvtStkMenageController implements MvtStkMenageApi {
	
	private final MvtStkMenageService mvtStkMenageService;
	
	@Autowired
	public MvtStkMenageController(MvtStkMenageService mvtStkMenageService) {
		this.mvtStkMenageService = mvtStkMenageService;
	}
	
	@Override
	public void productTypeDistrubution(ProductTypeDistributionDto dto) {
		
		mvtStkMenageService.productTypeDistribution(dto);
		
	}

	@Override
	public BigDecimal stockReelMenage(Long idProduitType, Long idMenage) {
		
		return mvtStkMenageService.stockReelMenage(idProduitType, idMenage);
	}

	@Override
	public Page<MvtStkMenageDto> findAllEntries(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkMenageService.findEntriesByProductTypeMenageLike(search, pageable);
	}

	@Override
	public List<MvtStkMenageDto> findMvtStkMenageByMenageId(Long idMenage) {
		
		return mvtStkMenageService.findMvtStkMenageByMenageId(idMenage);
	}

	@Override
	public Page<MvtStkMenageDto> findAllMvtStkMenages(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return mvtStkMenageService.findAllByProductTypeMenageLike(search, pageable);
	}
	
	
}
