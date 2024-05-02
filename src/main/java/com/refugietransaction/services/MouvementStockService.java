package com.refugietransaction.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.model.TypeMouvementStock;

public interface MouvementStockService {
	
	BigDecimal stockReelMenage(Long idProduit, Long idMenage);

	List<MouvementStockDto> mvtStkArticleMenage(Long idProduit, Long idMenage);

	MouvementStockDto entreeStock(MouvementStockDto dto);

	MouvementStockDto sortieStock(MouvementStockDto dto);
	
	//Pour le camp
	
	List<MouvementStockDto> entreeArticleCamp(Long idProduit, Long idCamp);
	
	List<MouvementStockDto> sortieArticleCamp(Long idProduit, Long idCamp);
	
	List<MouvementStockDto> entreeArticleCampPeriode(Long idProduit, Long idCamp, Date startDate, Date endDate);
	
	List<MouvementStockDto> sortieArticleCampPeriode(Long idProduit, Long idCamp, Date startDate, Date endDate);
	
	//Pour le menage
	
	List<MouvementStockDto> entreeArticleMenage(Long idProduit, Long idMenage);
	
	List<MouvementStockDto> sortieArticleMenage(Long idProduit, Long idMenage);
	
	List<MouvementStockDto> entreeArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate, Date endDate);
	
	List<MouvementStockDto> sortieArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate, Date endDate);
	
	
	//Pour l'utilisateur
	
	List<MouvementStockDto> entreeArticleUser(Long idProduit, Long idUser);
		
	List<MouvementStockDto> sortieArticleUser(Long idProduit, Long idUser);
		
	List<MouvementStockDto> entreeArticleUserPeriode(Long idProduit, Long idUser, Date startDate, Date endDate);
		
	List<MouvementStockDto> sortieArticleUserPeriode(Long idProduit, Long idUser, Date startDate, Date endDate);
	
}
