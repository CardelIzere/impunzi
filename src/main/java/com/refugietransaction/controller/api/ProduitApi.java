package com.refugietransaction.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.refugietransaction.dto.ProduitDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("produits")

public interface ProduitApi {

    @ApiOperation(value = "Créer un produit", notes = "Cette methode permet d'enregistrer ou modifier un produit", response = ProduitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet produit cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet produit n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/produits/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto save(@RequestBody ProduitDto dto);

    @ApiOperation(value = "Trouver un produit par son ID", notes = "Cette methode permet de chercher un produit par son ID", response = ProduitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun produit n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/produits/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto findById(@PathVariable("idProduit") Long idProduit);

    @ApiOperation(value = "Récupérer la liste de tous les produits", notes = "Cette methode permet de chercher et renvoyer la liste des produits qui existent" + "dans la BDD",
    		responseContainer = "List<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/produits/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProduitDto> findAll();

    @ApiOperation(value = "Supprimer un produit par son ID", notes = "Cette methode permet de supprimer un produit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/produits/delete/{idProduit}")
    void delete(@PathVariable("idProduit") Long id);
    
}
