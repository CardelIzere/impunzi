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

import com.refugietransaction.dto.MagasinierDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api("magasiniers")
public interface MagasinierApi {
	
	@ApiOperation(value = "Créer un magasinier", notes = "Cette methode permet d'enregistrer ou modifier un magasinier", response = MagasinierDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet magasinier cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet magasinier n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/magasiniers/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MagasinierDto save(@RequestBody MagasinierDto dto);

    @ApiOperation(value = "Trouver un magasinier par son ID", notes = "Cette methode permet de chercher un magasinier par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le magasinier a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun magasinier n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/magasiniers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    MagasinierDto findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Récupérer la liste de tous les maganisiers", notes = "Cette methode permet de chercher et renvoyer la liste des agents qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des magasiniers / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/magasiniers/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MagasinierDto> findAll();

    @ApiOperation(value = "Récupérer la liste des camps d'un fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des camps qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des camps / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/magasiniers/supplier/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MagasinierDto> findSupplierCamps(
            @PathVariable("idSupplier") Long idSupplier,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @ApiOperation(value = "Supprimer un fournisseur par son ID", notes = "Cette methode permet de supprimer un magasinier par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le magasinier a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/magasiniers/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
