package com.refugietransaction.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.MouvementStockApi;
import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.model.TypeMouvementStock;
import com.refugietransaction.services.MouvementStockService;

@RestController

public class MouvementStockController implements MouvementStockApi {
	
	private MouvementStockService mouvementStockService;
	
	@Autowired
	public MouvementStockController(MouvementStockService mouvementStockService) {
		this.mouvementStockService = mouvementStockService;
	}

	@Override
	public BigDecimal stockReelMenage(Long idProduit, Long idMenage) {
		return mouvementStockService.stockReelMenage(idProduit, idMenage);
	}

	@Override
	public List<MouvementStockDto> mvtStkArticleMenage(Long idProduit, Long idMenage) {
		return mouvementStockService.mvtStkArticleMenage(idProduit, idMenage);
	}

	@Override
	public MouvementStockDto entreeStock(MouvementStockDto dto) {
		return mouvementStockService.entreeStock(dto);
	}

	@Override
	public MouvementStockDto sortieStock(MouvementStockDto dto) {
		return mouvementStockService.sortieStock(dto);
	}
	
	//Pour le camp
	
	@Override
	public List<MouvementStockDto> entreeArticleCamp(Long idProduit, Long idCamp) {
		return mouvementStockService.entreeArticleCamp(idProduit, idCamp);
	}

	@Override
	public List<MouvementStockDto> sortieArticleCamp(Long idProduit, Long idCamp) {
		return mouvementStockService.sortieArticleCamp(idProduit, idCamp);
	}

	@Override
	public List<MouvementStockDto> entreeArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
			Date endDate) {
		
		return mouvementStockService.entreeArticleCampPeriode(idProduit, idCamp, startDate, endDate);
	}

	@Override
	public List<MouvementStockDto> sortieArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
			Date endDate) {
		
		return mouvementStockService.sortieArticleCampPeriode(idProduit, idCamp, startDate, endDate);
	}
	
	
	//Pour le menage
	
	@Override
	public List<MouvementStockDto> entreeArticleMenage(Long idProduit, Long idMenage) {
		
		return mouvementStockService.entreeArticleMenage(idProduit, idMenage);
	}

	@Override
	public List<MouvementStockDto> sortieArticleMenage(Long idProduit, Long idMenage) {
		
		return mouvementStockService.sortieArticleMenage(idProduit, idMenage);
	}

	@Override
	public List<MouvementStockDto> entreeArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
			Date endDate) {
		
		return mouvementStockService.entreeArticleMenagePeriode(idProduit, idMenage, startDate, endDate);
	}

	@Override
	public List<MouvementStockDto> sortieArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
			Date endDate) {
		
		return mouvementStockService.sortieArticleMenagePeriode(idProduit, idMenage, startDate, endDate);
	}
	
	
	//Pour l'agent

	@Override
	public List<MouvementStockDto> entreeArticleUser(Long idProduit, Long idUser) {
			
		return mouvementStockService.entreeArticleUser(idProduit, idUser);
	}

	@Override
	public List<MouvementStockDto> sortieArticleUser(Long idProduit, Long idUser) {
			
		return mouvementStockService.sortieArticleUser(idProduit, idUser);
	}

	@Override
	public List<MouvementStockDto> entreeArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
			Date endDate) {
			
		return mouvementStockService.entreeArticleUserPeriode(idProduit, idUser, startDate, endDate);
	}

	@Override
	public List<MouvementStockDto> sortieArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
			Date endDate) {
			
		return mouvementStockService.sortieArticleUserPeriode(idProduit, idUser, startDate, endDate);
	}

}
