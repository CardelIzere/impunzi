package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.ProduitApi;
import com.refugietransaction.dto.ProduitDto;
import com.refugietransaction.services.ProduitService;

@RestController

public class ProduitController implements ProduitApi {
	
	private ProduitService produitService;
	
	@Autowired
	public ProduitController(ProduitService produitService) {
		this.produitService = produitService;
	}

	@Override
	public ProduitDto save(ProduitDto dto) {
		return produitService.save(dto);
	}

	@Override
	public ProduitDto findById(Long idProduit) {
		return produitService.findById(idProduit);
	}

	@Override
	public List<ProduitDto> findAll() {
		return produitService.findAll();
	}

	@Override
	public void delete(Long id) {
		produitService.delete(id);
		
	}

}
