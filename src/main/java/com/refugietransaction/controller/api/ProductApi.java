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

import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("products")

public interface ProductApi {

    @ApiOperation(value = "Créer un produit", notes = "Cette methode permet d'enregistrer ou modifier un produit", response = ProductDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet produit cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet produit n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/products/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto save(@RequestBody ProductDto dto);

    @ApiOperation(value = "Trouver un produit par son ID", notes = "Cette methode permet de chercher un produit par son ID", response = ProductDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun produit n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/products/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto findById(@PathVariable("idProduit") Long idProduit);
    
    @ApiOperation(value = "Récupérer la liste de tous les produits", notes = "Cette methode permet de chercher et renvoyer la liste des produits qui existent" + "dans la BDD",
    		responseContainer = "List<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/products/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDto> findAll();

    @ApiOperation(value = "Récupérer la liste de tous les produits avec pagination", notes = "Cette methode permet de chercher et renvoyer la liste des produits qui existent" + "dans la BDD",
    		responseContainer = "Page<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/products/paginate-search-all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProductDto> findAllProduits(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @ApiOperation(value = "Récupérer la liste de tous les produits d'un fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des produits qui existent" + "dans la BDD",
    		responseContainer = "Page<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/products/suppliers/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProductDto> findSupplierProducts(
    		@PathVariable("idSupplier") Long idSupplier,
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @ApiOperation(value = "Récupérer la liste de tous les produits d'un fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des produits qui existent" + "dans la BDD",
    		responseContainer = "List<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des articles d'un fournisseur / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/products/all/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDto> findAllSupplierProducts(@PathVariable("idSupplier") Long idSupplier);
    
    @ApiOperation(value = "Récupérer la liste des produits vendues", notes = "Cette methode permet de chercher et renvoyer la liste des produits vendues qui existent" + "dans la BDD",
    		responseContainer = "List<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des articles vendues / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/products/sold-products", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDto> findSoldProducts();

    @ApiOperation(value = "Supprimer un produit par son ID", notes = "Cette methode permet de supprimer un produit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/products/delete/{idProduit}")
    void delete(@PathVariable("idProduit") Long idProduit);
    
}
