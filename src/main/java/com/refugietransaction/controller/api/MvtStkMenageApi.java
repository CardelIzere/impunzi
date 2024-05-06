package com.refugietransaction.controller.api;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping(value = Constants.APP_ROOT + "/mvtstkmenage/distribution", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void productTypeDistrubution(@RequestBody ProductTypeDistributionDto dto);
	
	@GetMapping(value = Constants.APP_ROOT + "/mvtstkmenage/stockReelMenage/{idProduitType}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal stockReelMenage(@PathVariable("idProduitType") Long idProduitType, @PathVariable("idMenage") Long idMenage);
    
    @GetMapping(value = Constants.APP_ROOT + "/mvtstkmenage/filter/produitType/menage/{idProduitType}/{idMenage}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MvtStkMenageDto> mvtStkProductTypeMenage(@PathVariable("idProduitType") Long idProduitType, @PathVariable("idMenage") Long idMenage);
}
