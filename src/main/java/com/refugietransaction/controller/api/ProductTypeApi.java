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

import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("product_types")
public interface ProductTypeApi {
	
	@ApiOperation(value = "Créer un type de produit", notes = "Cette methode permet d'enregistrer ou modifier un type de produit", response = ProductTypeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet type de produit cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet type de produit n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/product_types/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ProductTypeDto save(@RequestBody ProductTypeDto dto);

    @ApiOperation(value = "Trouver un type de produit par son ID", notes = "Cette methode permet de chercher un type de produit par son ID", response = ProductTypeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le type de produit a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun type de produit n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/product_types/{idProductType}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductTypeDto findById(@PathVariable("idProductType") Long idProductType);
    
    @ApiOperation(value = "Récupérer la liste de tous les type de produits", notes = "Cette methode permet de chercher et renvoyer la liste des type de produits qui existent" + "dans la BDD",
    		responseContainer = "List<ProductTypeDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des types des articles / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/product_types/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductTypeDto> findAll();

    @ApiOperation(value = "Récupérer la liste de tous les type de produits avec pagination", notes = "Cette methode permet de chercher et renvoyer la liste des type de produits qui existent" + "dans la BDD",
    		responseContainer = "List<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des types des article / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/product_types/paginate-search-all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProductTypeDto> findAllProductTypes(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );

    @ApiOperation(value = "Supprimer un type de produit par son ID", notes = "Cette methode permet de supprimer un type de produit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le type de produit a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/product_types/delete/{idProductType}")
    void delete(@PathVariable("idProductType") Long id);
}
