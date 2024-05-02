package com.refugietransaction.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.refugietransaction.dto.MenageDto;
import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("menages")

public interface MenageApi {

    @ApiOperation(value = "Créer un ménage", notes = "Cette methode permet d'enregistrer ou modifier un menage", response = MenageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet ménage cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet ménage n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/menages/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MenageDto save(@RequestBody MenageDto dto);

    @ApiOperation(value = "Trouver un ménage par son ID", notes = "Cette methode permet de chercher un menage par son ID", response = MenageDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le ménage a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun ménage n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/menages/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    MenageDto findById(@PathVariable("idMenage") Long idMenage);

    @ApiOperation(value = "Récupérer la liste de tous les ménages", notes = "Cette methode permet de chercher et renvoyer la liste des menages qui existent" + "dans la BDD",
    		responseContainer = "List<MenageDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des ménages / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/menages/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MenageDto> findAll();
    
    @ApiOperation(value = "Supprimer un menage par son ID", notes = "Cette methode permet de supprimer un menage")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Le menage a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT+ "/menages/delete/{idMenage}")
    void delete(@PathVariable("idMenage") Long id);
    
}
