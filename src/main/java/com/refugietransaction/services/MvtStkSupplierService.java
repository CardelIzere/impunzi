//package com.refugietransaction.services;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.util.Date;
//import java.util.List;
//
//import com.refugietransaction.dto.MvtStkSupplierDto;
//import com.refugietransaction.model.TypeMouvementStock;
//
//public interface MvtStkSupplierService {
//	
//	BigDecimal stockReelMenage(Long idProduit, Long idMenage);
//
//	List<MvtStkSupplierDto> mvtStkArticleMenage(Long idProduit, Long idMenage);
//
//	MvtStkSupplierDto entreeStock(MvtStkSupplierDto dto);
//
//	MvtStkSupplierDto sortieStock(MvtStkSupplierDto dto);
//	
//	//Pour le camp
//	
////	List<MouvementStockDto> entreeArticleCamp(Long idProduit, Long idCamp);
////	
////	List<MouvementStockDto> sortieArticleCamp(Long idProduit, Long idCamp);
////	
////	List<MouvementStockDto> entreeArticleCampPeriode(Long idProduit, Long idCamp, Date startDate, Date endDate);
////	
////	List<MouvementStockDto> sortieArticleCampPeriode(Long idProduit, Long idCamp, Date startDate, Date endDate);
//	
//	//Pour le menage
//	
//	List<MvtStkSupplierDto> entreeArticleMenage(Long idProduit, Long idMenage);
//	
//	List<MvtStkSupplierDto> sortieArticleMenage(Long idProduit, Long idMenage);
//	
//	List<MvtStkSupplierDto> entreeArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate, Date endDate);
//	
//	List<MvtStkSupplierDto> sortieArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate, Date endDate);
//	
//	
//	//Pour l'utilisateur
//	
//	List<MvtStkSupplierDto> entreeArticleUser(Long idProduit, Long idUser);
//		
//	List<MvtStkSupplierDto> sortieArticleUser(Long idProduit, Long idUser);
//		
//	List<MvtStkSupplierDto> entreeArticleUserPeriode(Long idProduit, Long idUser, Date startDate, Date endDate);
//		
//	List<MvtStkSupplierDto> sortieArticleUserPeriode(Long idProduit, Long idUser, Date startDate, Date endDate);
//	
//}
