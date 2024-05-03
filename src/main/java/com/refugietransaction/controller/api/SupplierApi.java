package com.refugietransaction.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.dto.SupplierListDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("suppliers")
public interface SupplierApi  {
	
	@ApiOperation(value = "Créer un fournisseur", notes = "Cette methode permet d'enregistrer ou modifier un fournisseur", response = SupplierDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet fournisseur cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet fournisseur n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/suppliers/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SupplierDto save(@RequestBody SupplierDto dto);

    @ApiOperation(value = "Trouver un fournisseur par son ID", notes = "Cette methode permet de chercher un fournisseur par son ID", response = SupplierDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun fournisseur n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/suppliers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    SupplierDto findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Récupérer la liste de tous les fournisseurs", notes = "Cette methode permet de chercher et renvoyer la liste des fournisseurs qui existent" + "dans la BDD",
    		responseContainer = "List<SupplierDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des fournisseurs / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/suppliers/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<SupplierListDto> findAllSuppliers(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @ApiOperation(value = "Supprimer un fournisseur par son ID", notes = "Cette methode permet de supprimer un fournisseur")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Le fournisseur a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT+ "/suppliers/delete/{id}")
    void delete(@PathVariable("id") Long id);
    
    @ApiOperation(value = "Activer un fournisseur par son ID", notes = "Cette methode permet d'activer un fournisseur par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a ete active")
    })
    @PutMapping(value = Constants.APP_ROOT + "/suppliers/enable/{id}")
    void enableSupplier(@PathVariable("id") Long id);

    @ApiOperation(value = "Desactiver un fournisseur par son ID", notes = "Cette methode permet desactiver un fournisseur par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a ete desactivee")
    })
    @PutMapping(value = Constants.APP_ROOT + "/suppliers/desable/{id}")
    void desableSupplier(@PathVariable("id") Long id);
}
