package com.refugietransaction.controller.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.refugietransaction.dto.ByCampStockDto;
import com.refugietransaction.dto.CampStockDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductCampStockDto;
import com.refugietransaction.dto.ProductStockQuantityDto;
import com.refugietransaction.dto.VenteListDto;
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
	
	@ApiOperation(value = "Récupérer la liste des entrées d'un fournisseur avec pagination", notes = "Cette methode permet de chercher et renvoyer la liste des entrées qui existent" + "dans la BDD",
    		responseContainer = "Page<MvtStkSuppliersDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des entrées / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/entries/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findAllSupplierEntries(
    		@PathVariable("idSupplier") Long idSupplier,
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des sorties d'un fournisseur par camp avec pagination", notes = "Cette methode permet de chercher et renvoyer la liste des entrées qui existent" + "dans la BDD",
    		responseContainer = "Page<MvtStkSuppliersDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des sorties / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/sorties/{idCamp}/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findAllSorties(
    		@PathVariable("idCamp") Long idCamp,
    		@PathVariable("idSupplier") Long idSupplier,
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des sorties d'un fournisseur avec pagination", notes = "Cette methode permet de chercher et renvoyer la liste des entrées qui existent" + "dans la BDD",
    		responseContainer = "Page<MvtStkSuppliersDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des sorties / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/sorties/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findAllSupplierSorties(
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
	
	@ApiOperation(value = "Récupérer l'etat du stock d'un fournisseur dans chaque camp ", notes = "Cette methode permet de chercher et renvoyer la liste de stock d'un produit qui existent" + "dans la BDD",
            responseContainer = "Liste<CampStockDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste de l'etat du stock / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/supplier-stock-quantity-groupby-camp/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CampStockDto> getStockQuantities(@PathVariable("supplierId") Long supplierId);
	
	@ApiOperation(value = "Récupérer l'etat du stock d'un produit dans chaque camp ", notes = "Cette methode permet de chercher et renvoyer la liste de stock d'un produit qui existent" + "dans la BDD",
            responseContainer = "Liste<ProductStockQuantityDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste de l'etat du stock / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/product-stock-quantity-groupby-camp/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductStockQuantityDto> getProductStockQuantities(@PathVariable("productId") Long productId);
	
	@ApiOperation(value = "Récupérer la liste des produits d'un supplier dans un camp donné avec la quantite en stock", notes = "Cette methode permet de chercher et renvoyer la liste des produits avec la quantite du stock qui existent" + "dans la BDD",
    		responseContainer = "Page<ByCampStockDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des produits avec la quantite de stock par camp donné / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/list-product-supplier-camp-with-quantity/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ByCampStockDto> getTotalQuantityByIdCampIdSupplier(
    		@PathVariable("idSupplier") Long idSupplier, 
    		@PathVariable("idCamp") Long idCamp,
    		@RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des produits d'un supplier dans un camp donné avec la quantite en stock", notes = "Cette methode permet de chercher et renvoyer la liste des produits avec la quantite du stock qui existent" + "dans la BDD",
    		responseContainer = "List<MvtStkSupplierDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des produits avec la quantite de stock par camp donné / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/product-mvtstk-supplier-camp/{idProduct}/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> getProductMvtStkByIdCampIdSupplier(
    		@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    		@PathVariable("idProduct") Long idProduct, 
    		@PathVariable("idSupplier") Long idSupplier, 
    		@PathVariable("idCamp") Long idCamp,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des entrees par fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des entrees qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des entrees / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/entries/list/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findSupplierEntries(
    	    @PathVariable("idSupplier") Long idSupplier,

    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des entrees par fournisseur et par camp", notes = "Cette methode permet de chercher et renvoyer la liste des entrees qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des entrees / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/entries-supplier-camp/list/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findSupplierAndCampEntries(
    	    @PathVariable("idSupplier") Long idSupplier,
    	    @PathVariable("idCamp") Long idCamp,
    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des sorties par fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des sorties qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des sorties / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/sorties/list/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findSupplierSorties(
    	    @PathVariable("idSupplier") Long idSupplier,

    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des sorties par fournisseur et par camp", notes = "Cette methode permet de chercher et renvoyer la liste des sorties qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des sorties / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/sorties-supplier-camp/list/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findSupplierAndCampSorties(
    	    @PathVariable("idSupplier") Long idSupplier,
    	    @PathVariable("idCamp") Long idCamp,
    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des entrees par produit et par fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des entrees qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des entrees / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/entries/nouveau-mvt-stock-produit-by-camp/{idProduct}/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findProductSupplierEntries(
    	    @PathVariable("idSupplier") Long idSupplier,
    	    @PathVariable("idProduct") Long idProduct,
    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des entrees par produit, fournisseur et par camp", notes = "Cette methode permet de chercher et renvoyer la liste des entrees qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des entrees / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/entries/nouveau-mvt-stock-produit-camp/{idProduct}/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findProductAndSupplierAndCampEntries(
    		@PathVariable("idProduct") Long idProduct,
    	    @PathVariable("idSupplier") Long idSupplier,
    	    @PathVariable("idCamp") Long idCamp,
    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des sorties par produit et par fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des sorties qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des sorties / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/sorties/nouveau-mvt-stock-produit-by-camp/{idProduct}/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findProductSupplierSorties(
    		@PathVariable("idProduct") Long idProduct,
    	    @PathVariable("idSupplier") Long idSupplier,
    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Récupérer la liste des sorties par produit, par fournisseur et par camp", notes = "Cette methode permet de chercher et renvoyer la liste des sorties qui existent" + "dans la BDD",
    	    responseContainer = "Page<MvtStkSupplierDto>")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "La liste des sorties / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstksuppliers/sorties/nouveau-mvt-stock-produit-camp/{idProduct}/{idSupplier}/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkSupplierDto> findProductAndSupplierAndCampSorties(
    		@PathVariable("idProduct") Long idProduct,
    	    @PathVariable("idSupplier") Long idSupplier,
    	    @PathVariable("idCamp") Long idCamp,
    	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    	    @RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size
    );
	
	@ApiOperation(value = "Supprimer un mouvement stock d'un fournisseur par son ID", notes = "Cette methode permet de supprimer un mouvement stock d'un fournisseur")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Le mouvement stock a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT+ "/mvtstksuppliers/delete/{idMvtStkSupplier}")
    void delete(@PathVariable("idMvtStkSupplier") Long idMvtStkSupplier);
    
    
}

