package com.refugietransaction.controller.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.refugietransaction.dto.LigneVenteDto;
import com.refugietransaction.dto.VenteListDto;
import com.refugietransaction.dto.VentesDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("ventes")
public interface VentesApi {
	
	@ApiOperation(value = "Créer une vente", notes = "Cette methode permet d'enregistrer ou modifier une vente", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet vente cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet vente n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/ventes/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto save(@RequestBody VentesDto dto);

    @ApiOperation(value = "Trouver une vente par son ID", notes = "Cette methode permet de chercher une vente par son ID", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La vente a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune vente n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/ventes/{idVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto findById(@PathVariable("idVente") Long idVente);
    
    @ApiOperation(value = "Récupérer la liste de tous les ventes", notes = "Cette methode permet de chercher et renvoyer la liste des ventes qui existent" + "dans la BDD",
    		responseContainer = "List<VentesDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des ventes / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/ventes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<VentesDto> findAll();
    
    @ApiOperation(value = "Récupérer la liste des ventes par camp et fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des ventes qui existent" + "dans la BDD",
    		responseContainer = "Page<VenteListDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des ventes / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/ventes/all/{idCamp}/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<VenteListDto> findCampSupplierVentes(
    		@PathVariable("idCamp") Long idCamp,
    		@PathVariable("idSupplier") Long idSupplier,
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @ApiOperation(value = "Récupérer la liste des produits vendues", notes = "Cette methode permet de chercher et renvoyer la liste des ventes qui existent" + "dans la BDD",
    		responseContainer = "List<LigneVenteDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des produits vendues / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/ventes/sold-products/{idVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findSoldProductsInAllSales(@PathVariable("idVente") Long idVente);
    
    @ApiOperation(value = "Récupérer la liste des ventes par fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des ventes qui existent" + "dans la BDD",
    	    responseContainer = "Page<VenteListDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des ventes / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/ventes/list/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<VenteListDto> findSupplierVentes(
    	    @PathVariable("idSupplier") Long idSupplier,

    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @ApiOperation(value = "Récupérer la liste des ventes par fournisseur et par camp", notes = "Cette methode permet de chercher et renvoyer la liste des ventes qui existent" + "dans la BDD",
    	    responseContainer = "Page<VenteListDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des ventes / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/ventes/list/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<VenteListDto> findSupplierAndCampVentes(
    	    @PathVariable("idSupplier") Long idSupplier,
    	    @PathVariable("idCamp") Long idCamp,
    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @GetMapping(value = Constants.APP_ROOT + "/ventes/sum/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal sumSupplierVentes(
            @PathVariable("idSupplier") Long idSupplier,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(value = "search", required = false) String search
    );
    
    @GetMapping(value = Constants.APP_ROOT + "/ventes/sum/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal sumSupplierAndCampVentes(
            @PathVariable("idSupplier") Long idSupplier,
            @PathVariable("idCamp") Long idCamp,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(value = "search", required = false) String search
    );
    
    @ApiOperation(value = "Supprimer une vente par son ID", notes = "Cette methode permet de supprimer une vente par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La vente a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/ventes/delete/{idVente}")
    void delete(@PathVariable("idVente") Long idVente);
}
