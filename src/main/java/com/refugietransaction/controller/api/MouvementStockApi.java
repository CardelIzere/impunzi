package com.refugietransaction.controller.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.model.TypeMouvementStock;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("mouvementStocks")

public interface MouvementStockApi {

    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/stockReelMenage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal stockReelMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/filter/produit/menage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> mvtStkArticleMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
    
    @PostMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree", produces = MediaType.APPLICATION_JSON_VALUE)
    MouvementStockDto entreeStock(@RequestBody MouvementStockDto dto);
    
    @PostMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie", produces = MediaType.APPLICATION_JSON_VALUE)
    MouvementStockDto sortieStock(@RequestBody MouvementStockDto dto);
    
    //Pour le camp
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/camp/{idProduit}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> entreeArticleCamp(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/camp/{idProduit}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> sortieArticleCamp(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/camp/periode/{idProduit}/{idCamp}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> entreeArticleCampPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/camp/periode/{idProduit}/{idCamp}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> sortieArticleCampPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
    
    //Pour le menage
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/menage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> entreeArticleMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/menage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> sortieArticleMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/menage/periode/{idProduit}/{idMenage}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> entreeArticleMenagePeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/menage/periode/{idProduit}/{idMenage}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> sortieArticleMenagePeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
    
    //Pour l'utilisateur
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/user/{idProduit}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> entreeArticleUser(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/user/{idProduit}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> sortieArticleUser(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/user/periode/{idProduit}/{idUser}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> entreeArticleUserPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
    
    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/user/periode/{idProduit}/{idUser}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MouvementStockDto> sortieArticleUserPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
    
    
    
    
}

