package com.refugietransaction.controller.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.model.TypeMvtStkSupplier;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("mvtstksuppliers")

public interface MvtStkSupplierApi {
	
	
	@ApiOperation(value = "Créer les entrées", notes = "Cette methode permet d'enregistrer ou modifier un entrée", response = MvtStkSupplierDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet entree cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet entree n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStkSupplierDto save(@RequestBody MvtStkSupplierDto dto);
	
	@ApiOperation(value = "Récupérer la liste des entrées d'un fournisseur par camp avec pagination", notes = "Cette methode permet de chercher et renvoyer la liste des entrées qui existent" + "dans la BDD",
    		responseContainer = "Page<MvtStkSuppliersDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des entrées / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/entries/{idCamp}/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findAllEntries(
    		@PathVariable("idCamp") Long idCamp,
    		@PathVariable("idSupplier") Long idSupplier,
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer l'etat du stock d'un fournisseur par camp", notes = "Cette methode permet de chercher et renvoyer l'etat du stock qui existent" + "dans la BDD",
    		responseContainer = "Liste<MvtStkSuppliersDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L' etat du stock / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/etat-stock/{idCamp}/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MvtStkSupplierDto> findMvtStkSupplierBySupplierAndCamp(
    		@PathVariable("idCamp") Long idCamp,
    		@PathVariable("idSupplier") Long idSupplier
    );
	
	@ApiOperation(value = "Récupérer la liste des mouvements de stock", notes = "Cette methode permet de chercher et renvoyer la liste des mouvements de stock qui existent" + "dans la BDD",
    		responseContainer = "Page<MvtStkSuppliersDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Mvt de stock / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findAllMvtStkSuppliers(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des mouvements de stock d'un produit donné", notes = "Cette methode permet de chercher et renvoyer la liste de stock d'un produit qui existent" + "dans la BDD",
    		responseContainer = "Liste<MvtStkSuppliersDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste du mouvement de stock d'un produit donné / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/all/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MvtStkSupplierDto> findMvtStkSupplierByProductId(@PathVariable("idProduit") Long idProduit);

//    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/stockReelMenage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
//    BigDecimal stockReelMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/filter/produit/menage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> mvtStkArticleMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
//    
//    @PostMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/entree", produces = MediaType.APPLICATION_JSON_VALUE)
//    MvtStkSupplierDto entreeStock(@RequestBody MvtStkSupplierDto dto);
//    
//    @PostMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/sortie", produces = MediaType.APPLICATION_JSON_VALUE)
//    MvtStkSupplierDto sortieStock(@RequestBody MvtStkSupplierDto dto);
    
    //Pour le camp
    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/camp/{idProduit}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MouvementStockDto> entreeArticleCamp(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/camp/{idProduit}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MouvementStockDto> sortieArticleCamp(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/camp/periode/{idProduit}/{idCamp}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MouvementStockDto> entreeArticleCampPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/camp/periode/{idProduit}/{idCamp}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MouvementStockDto> sortieArticleCampPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idCamp") Long idCamp, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
    
    //Pour le menage
    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/menage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> entreeArticleMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/menage/{idProduit}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> sortieArticleMenage(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/menage/periode/{idProduit}/{idMenage}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> entreeArticleMenagePeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/menage/periode/{idProduit}/{idMenage}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> sortieArticleMenagePeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idMenage") Long idMenage, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
//    
//    //Pour l'utilisateur
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/user/{idProduit}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> entreeArticleUser(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/user/{idProduit}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> sortieArticleUser(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/entree/produit/user/periode/{idProduit}/{idUser}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> entreeArticleUserPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
//    
//    @GetMapping(value = Constants.APP_ROOT + "/mouvementStocks/sortie/produit/user/periode/{idProduit}/{idUser}/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<MvtStkSupplierDto> sortieArticleUserPeriode(@PathVariable("idProduit") Long idProduit, @PathVariable("idUser") Long idUser, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") Date endDate);
//    
//    
    
    
}

