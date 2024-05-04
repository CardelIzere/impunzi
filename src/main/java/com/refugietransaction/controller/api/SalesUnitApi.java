package com.refugietransaction.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.refugietransaction.dto.SalesUnitDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("sales_units")
public interface SalesUnitApi {
	
	@ApiOperation(value = "Créer une unité de vente", notes = "Cette methode permet d'enregistrer ou modifier une unité de vente", response = SalesUnitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet unité de vente cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet unité de vente n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/sales_units/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SalesUnitDto save(@RequestBody SalesUnitDto dto);

    @ApiOperation(value = "Trouver une unité de vente par son ID", notes = "Cette methode permet de chercher une unité de vente par son ID", response = SalesUnitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'unité de vente a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune unité de vente n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/sales_units/{idSalesUnit}", produces = MediaType.APPLICATION_JSON_VALUE)
    SalesUnitDto findById(@PathVariable("idSalesUnit") Long idSalesUnit);
    
    @ApiOperation(value = "Récupérer la liste de tous les unités de vente", notes = "Cette methode permet de chercher et renvoyer la liste des unités de vente qui existent" + "dans la BDD",
    		responseContainer = "List<SalesUnitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des unités de vente / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/sales_units/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SalesUnitDto> findAll();

    @ApiOperation(value = "Récupérer la liste de tous les unités de vente avec pagination", notes = "Cette methode permet de chercher et renvoyer la liste des unités de vente qui existent" + "dans la BDD",
    		responseContainer = "List<SalesUnitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des unités de vente / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/sales_units/paginate-search-all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<SalesUnitDto> findAllSalesUnits(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );

    @ApiOperation(value = "Supprimer une unité de vente par son ID", notes = "Cette methode permet de supprimer une unité de vente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'unité de vente a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/sales_units/delete/{idSalesUnit}")
    void delete(@PathVariable("idSalesUnit") Long id);
}
