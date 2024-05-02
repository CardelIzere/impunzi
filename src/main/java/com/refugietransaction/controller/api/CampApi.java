package com.refugietransaction.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.refugietransaction.dto.CampDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("camps")
public interface CampApi {

    @ApiOperation(value = "Créer un camp", notes = "Cette methode permet d'enregistrer ou modifier un camp", response = CampDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet camp cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet camp n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/camps/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CampDto save(@RequestBody CampDto dto);

    @ApiOperation(value = "Trouver un camp par son ID", notes = "Cette methode permet de chercher un camp par son ID", response = CampDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le camp a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun camp n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/camps/{idCamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    CampDto findById(@PathVariable("idCamp") Long idCamp);

    @ApiOperation(value = "Récupérer la liste de tous les camps", notes = "Cette methode permet de chercher et renvoyer la liste des camps qui existent" + "dans la BDD",
    		responseContainer = "List<CampDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/camps/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CampDto> findAll();

    @ApiOperation(value = "Supprimer un camp par son ID", notes = "Cette methode permet de supprimer un camp par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le camp a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/camps/delete/{idCamp}")
    void delete(@PathVariable("idCamp") Long id);
}
