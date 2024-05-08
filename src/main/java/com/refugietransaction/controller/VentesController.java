package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.VentesApi;
import com.refugietransaction.dto.VenteListDto;
import com.refugietransaction.dto.VentesDto;
import com.refugietransaction.services.VentesService;

@RestController
public class VentesController implements VentesApi {
	
	private final VentesService ventesService;
	
	@Autowired
	public VentesController(VentesService ventesService) {
		this.ventesService = ventesService;
	}
	
	@Override
	public VentesDto save(VentesDto dto) {
		
		return ventesService.save(dto);
	}

	@Override
	public VentesDto findById(Long idVente) {
		
		return ventesService.findById(idVente);
	}

	@Override
	public List<VentesDto> findAll() {
		
		return ventesService.findAll();
	}

	@Override
	public void delete(Long idVente) {
		ventesService.delete(idVente);
		
	}

	@Override
	public Page<VenteListDto> findAllVentes(Long idCamp, Long idSupplier, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return ventesService.findCampSupplierVentesBySupplierProductNameLike(idCamp, idSupplier, search, pageable);
	}

}
