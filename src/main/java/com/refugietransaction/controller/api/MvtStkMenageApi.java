package com.refugietransaction.controller.api;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.refugietransaction.dto.CampDto;
import com.refugietransaction.dto.MvtStkMenageDto;
import com.refugietransaction.dto.ProductTypeDistributionDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api("mvtstkmenage")
public interface MvtStkMenageApi {
	
	@ApiOperation(value = "Distribuer des produits ", notes = "Cette methode permet de distribuer des produits dans les menages", response = CampDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Distribution effectuee"),
            @ApiResponse(code = 400, message = "L'objet Distribution n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/mvtstkmenage/distribution/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void productTypeDistrubution(@RequestBody ProductTypeDistributionDto dto);
	
	@GetMapping(value = Constants.APP_ROOT + "/mvtstkmenage/stockReelMenage/{idProduitType}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal stockReelMenage(@PathVariable("idProduitType") Long idProduitType, @PathVariable("idMenage") Long idMenage);
    
    @ApiOperation(value = "Récupérer la liste des entrées", notes = "Cette methode permet de chercher et renvoyer la liste des entrées qui existent" + "dans la BDD",
    		responseContainer = "Page<MvtStkMenageDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des entrées / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/mvtstkmenage/distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<MvtStkMenageDto> findAllEntries(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    		@RequestParam(value = "size", defaultValue = "10") int size
    );
    
}
