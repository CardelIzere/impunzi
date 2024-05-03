package com.refugietransaction.controller.api;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.refugietransaction.dto.UserDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api("users")
public interface UserApi {
	
	@ApiOperation(value = "Trouver un utilisateur par son ID", notes = "Cette methode permet de chercher un utilisateur par son ID", response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Récupérer la liste de tous les utilisateurs", notes = "Cette methode permet de chercher et renvoyer la liste des utilisateurs qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des utilisateurs / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/users/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<UserDto> findAllUsers(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );

    @ApiOperation(value = "Activer un utilisateur par son ID", notes = "Cette methode permet d'activer un utilisateur par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a ete active")
    })
    @PutMapping(value = Constants.APP_ROOT + "/users/enable/{id}")
    void enableUser(@PathVariable("id") Long id);

    @ApiOperation(value = "Desactiver un utilisateur par son ID", notes = "Cette methode permet desactiver un utilisateur par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a ete deactivee")
    })
    @PutMapping(value = Constants.APP_ROOT + "/users/desable/{id}")
    void desableUser(@PathVariable("id") Long id);
}
