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

import com.refugietransaction.dto.AdminDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api("suppliers")
public interface AdminApi {
	
	@ApiOperation(value = "Créer un admin", notes = "Cette methode permet d'enregistrer ou modifier un admin", response = AdminDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet admin cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet admin n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/admins/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AdminDto save(@RequestBody AdminDto dto);

    @ApiOperation(value = "Trouver un admin par son ID", notes = "Cette methode permet de chercher un admin par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'admin a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun admin n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    AdminDto findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Récupérer la liste de tous les admins", notes = "Cette methode permet de chercher et renvoyer la liste des admins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des admins / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AdminDto> findAll();

    @ApiOperation(value = "Récupérer la liste de tous les admins principaux", notes = "Cette methode permet de chercher et renvoyer la liste des admins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des admins principaux / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/main", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<AdminDto> findMainAdmins(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @ApiOperation(value = "Récupérer la liste de tous les admins d'une entreprise", notes = "Cette methode permet de chercher et renvoyer la liste des admins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des admins principaux / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/admins/supplier/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<AdminDto> findSupplierAdmins(
            @PathVariable("idSupplier") Long idSupplier,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @ApiOperation(value = "Supprimer un admin par son ID", notes = "Cette methode permet de supprimer un admin par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'admin a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/admins/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
