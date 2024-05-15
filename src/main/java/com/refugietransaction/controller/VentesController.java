package com.refugietransaction.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.VentesApi;
import com.refugietransaction.dto.LigneVenteDto;
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
	public Page<VenteListDto> findCampSupplierVentes(Long idCamp, Long idSupplier, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return ventesService.findCampSupplierVentesBySupplierPersonneContactLike(idCamp, idSupplier, search, pageable);
	}

	@Override
	public List<LigneVenteDto> findSoldProductsInAllSales(Long idVente) {
		
		return ventesService.findSoldProductsInAllSales(idVente);
	}

	@Override
	public Page<VenteListDto> findSupplierVentes(Long idSupplier, Date startDate, Date endDate, String search, int page,
			int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ventesService.findSupplierVentes(startDate, endDate, idSupplier, search, pageable);
	}

	@Override
	public BigDecimal sumSupplierVentes(Long idSupplier, Date startDate, Date endDate, String search) {
		return ventesService.sumSupplierVentes(startDate, endDate, idSupplier, search);
	}

	@Override
	public Page<VenteListDto> findSupplierAndCampVentes(Long idSupplier, Long idCamp, Date startDate, Date endDate,
			String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ventesService.findSupplierAndCampVentes(startDate, endDate, idSupplier, idCamp, search, pageable);
	}

	@Override
	public BigDecimal sumSupplierAndCampVentes(Long idSupplier, Long idCamp, Date startDate, Date endDate,
			String search) {

		return ventesService.sumSupplierAndCampVentes(startDate, endDate, idSupplier, idCamp, search);
	}

	

}
