package com.refugietransaction.controller.api;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.refugietransaction.dto.SuperadminDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api("superadmins")
public interface SuperadminApi {
	
	@ApiOperation(value = "Créer un superadmin", notes = "Cette methode permet d'enregistrer ou modifier un superadmin", response = SuperadminDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet superadmin cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet superadmin n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/superadmins/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	SuperadminDto save(@RequestBody SuperadminDto dto);

    @ApiOperation(value = "Trouver un superadmin par son ID", notes = "Cette methode permet de chercher un superamdin par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le superadmin a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune superadmin n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/superadmins/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    SuperadminDto findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Récupérer la liste de tous les superadmins", notes = "Cette methode permet de chercher et renvoyer la liste des superadmins qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des superadmins / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/superadmins/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<SuperadminDto> findByNameEmailPhoneLike(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @ApiOperation(value = "Supprimer un superadmin par son ID", notes = "Cette methode permet de supprimer un superadmin par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le superadmin a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/superadmins/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
