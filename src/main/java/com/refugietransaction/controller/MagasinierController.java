package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.MagasinierApi;
import com.refugietransaction.dto.MagasinierDto;
import com.refugietransaction.services.MagasinierService;

@RestController
public class MagasinierController implements MagasinierApi {
	
	private final MagasinierService magasinierService;

	@Autowired
	public MagasinierController(MagasinierService magasinierService) {
		this.magasinierService = magasinierService;
	}

	@Override
	public MagasinierDto save(MagasinierDto dto) {

		return magasinierService.save(dto);
	}

	@Override
	public MagasinierDto findById(Long id) {

		return magasinierService.findById(id);
	}

	@Override
	public Page<MagasinierDto> findSupplierCamps(Long idSupplier, String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		return magasinierService.findSupplierMagasiniers(idSupplier,search,pageable);
	}

	@Override
	public void delete(Long id) {
		magasinierService.delete(id);

	}

	@Override
	public List<MagasinierDto> findAll() {
		
		return magasinierService.findAll();
	}
}
