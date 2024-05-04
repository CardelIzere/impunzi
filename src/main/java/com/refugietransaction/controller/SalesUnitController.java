package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.SalesUnitApi;
import com.refugietransaction.dto.SalesUnitDto;
import com.refugietransaction.services.SalesUnitService;

@RestController
public class SalesUnitController implements SalesUnitApi {
	
	private final SalesUnitService salesUnitService;
	
	@Autowired
	public SalesUnitController(SalesUnitService salesUnitService) {
		this.salesUnitService = salesUnitService;
	}
	
	@Override
	public SalesUnitDto save(SalesUnitDto dto) {
		// TODO Auto-generated method stub
		return salesUnitService.save(dto);
	}

	@Override
	public SalesUnitDto findById(Long idSalesUnit) {
		// TODO Auto-generated method stub
		return salesUnitService.findById(idSalesUnit);
	}

	@Override
	public List<SalesUnitDto> findAll() {
		// TODO Auto-generated method stub
		return salesUnitService.findAll();
	}

	@Override
	public Page<SalesUnitDto> findAllSalesUnits(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return salesUnitService.findByNameLike(search, pageable);
	}

	@Override
	public void delete(Long id) {
		salesUnitService.delete(id);
		
	}
	
}
