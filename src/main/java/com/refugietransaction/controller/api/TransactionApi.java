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

import com.refugietransaction.dto.TransactionDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("transactions")
public interface TransactionApi {
	
	@ApiOperation(value = "Créer une transaction", notes = "Cette methode permet d'enregistrer ou modifier une transaction", response = TransactionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet transaction cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet transaction n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/transactions/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TransactionDto save(@RequestBody TransactionDto dto);

    @ApiOperation(value = "Trouver une transaction par son ID", notes = "Cette methode permet de chercher une transaction par son ID", response = TransactionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La transaction a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune transaction n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/transactions/{idTransaction}", produces = MediaType.APPLICATION_JSON_VALUE)
    TransactionDto findById(@PathVariable("idTransaction") Long idTransaction);
    
    @ApiOperation(value = "Récupérer la liste de tous les transactions", notes = "Cette methode permet de chercher et renvoyer la liste des transactions qui existent" + "dans la BDD",
    		responseContainer = "List<TransactionDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des transactions / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/transactions/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<TransactionDto> findAll();
    
    @ApiOperation(value = "Récupérer la liste des transactions par camp et fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des transactions qui existent" + "dans la BDD",
    		responseContainer = "Page<TransactionDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des transactions / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/transactions/list/{idCamp}/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<TransactionDto> findCampSupplierTransactions(
    		@PathVariable("idCamp") Long idCamp,
    		@PathVariable("idSupplier") Long idSupplier,
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @ApiOperation(value = "Récupérer la liste des transactions par fournisseur", notes = "Cette methode permet de chercher et renvoyer la liste des ventes qui existent" + "dans la BDD",
    		responseContainer = "Page<TransactionDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des transactions / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/transactions/list/{idSupplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<TransactionDto> findSupplierTransactions(
    		@PathVariable("idSupplier") Long idSupplier,
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @ApiOperation(value = "Supprimer une transaction par son ID", notes = "Cette methode permet de supprimer un produit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La transaction a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/products/delete/{idTransaction}")
    void delete(@PathVariable("idTransaction") Long idTransaction);
}
