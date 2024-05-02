package com.refugietransaction.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.MouvementStockDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.TypeMouvementStock;
import com.refugietransaction.repository.MouvementStockRepository;
import com.refugietransaction.services.MouvementStockService;
import com.refugietransaction.validator.MouvementStockValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MouvementStockServiceImpl implements MouvementStockService {
	
	private MouvementStockRepository mouvementStockRepository;
	
	@Autowired
	public MouvementStockServiceImpl(MouvementStockRepository mouvementStockRepository) {
		this.mouvementStockRepository = mouvementStockRepository;
	}

	@Override
	public BigDecimal stockReelMenage(Long idProduit, Long idMenage) {
		
		if(idProduit == null) {
			log.warn("ID produit is NULL");
			return BigDecimal.valueOf(-1);
		}
		
		if(idMenage == null) {
			log.warn("ID menage is NULL");
			return BigDecimal.valueOf(-1);
		}
		
		return mouvementStockRepository.stockReelMenage(idProduit, idMenage);
	}

	@Override
	public List<MouvementStockDto> mvtStkArticleMenage(Long idProduit, Long idMenage) {
		
		return mouvementStockRepository.findAllByArticleIdAndMenageId(idProduit, idMenage).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public MouvementStockDto entreeStock(MouvementStockDto dto) {
		return entreePositive(dto, TypeMouvementStock.ENTREE);
	}

	@Override
	public MouvementStockDto sortieStock(MouvementStockDto dto) {
		return sortiePositive(dto, TypeMouvementStock.SORTIE);
	}
	
	private MouvementStockDto entreePositive(MouvementStockDto dto, TypeMouvementStock typeMouvement) {
		
		List<String> errors = MouvementStockValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Article is not valid {}", dto);
			throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
		}
		dto.setQuantite(
				BigDecimal.valueOf(
						Math.abs(dto.getQuantite().doubleValue())
						)
				);
		dto.setTypeMouvement(typeMouvement);
		
		return MouvementStockDto.fromEntity(
				mouvementStockRepository.save(MouvementStockDto.toEntity(dto))
		);
	}
	
	private MouvementStockDto sortiePositive(MouvementStockDto dto, TypeMouvementStock typeMouvement) {
		
		List<String> errors = MouvementStockValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Article is not valid {}", dto);
			throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
		}
		dto.setQuantite(
				BigDecimal.valueOf(
						Math.abs(dto.getQuantite().doubleValue())
						)
				);
		dto.setTypeMouvement(typeMouvement);
		
		return MouvementStockDto.fromEntity(
				mouvementStockRepository.save(MouvementStockDto.toEntity(dto))
		);
	}
	
	//Pour le camp

	@Override
	public List<MouvementStockDto> entreeArticleCamp(Long idProduit, Long idCamp) {
		
		return mouvementStockRepository.findEntreeByIdProduitIdCamp(idProduit, idCamp).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> sortieArticleCamp(Long idProduit, Long idCamp) {
		
		return mouvementStockRepository.findSortieByIdProduitIdCamp(idProduit, idCamp).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> entreeArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
			Date endDate) {
		
		return mouvementStockRepository.findEntreeByIdProduitIdCampPeriode(idProduit, idCamp, startDate, endDate).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> sortieArticleCampPeriode(Long idProduit, Long idCamp, Date startDate,
			Date endDate) {
		
		return mouvementStockRepository.findSortieByIdProduitIdCampPeriode(idProduit, idCamp, startDate, endDate).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}
	
	//Pour le menage

	@Override
	public List<MouvementStockDto> entreeArticleMenage(Long idProduit, Long idMenage) {
		
		return mouvementStockRepository.findEntreeByIdProduitIdMenage(idProduit, idMenage).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> sortieArticleMenage(Long idProduit, Long idMenage) {
		
		return mouvementStockRepository.findSortieByIdProduitIdMenage(idProduit, idMenage).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> entreeArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
			Date endDate) {
		
		return mouvementStockRepository.findEntreeByIdProduitIdMenagePeriode(idProduit, idMenage, startDate, endDate).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> sortieArticleMenagePeriode(Long idProduit, Long idMenage, Date startDate,
			Date endDate) {
		
		return mouvementStockRepository.findSortieByIdProduitIdMenagePeriode(idProduit, idMenage, startDate, endDate).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<MouvementStockDto> entreeArticleUser(Long idProduit, Long idUser) {
		
		return mouvementStockRepository.findEntreeByIdProduitIdAgent(idProduit, idUser).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> sortieArticleUser(Long idProduit, Long idUser) {
		
		return mouvementStockRepository.findSortieByIdProduitIdAgent(idProduit, idUser).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> entreeArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
			Date endDate) {
		
		return mouvementStockRepository.findEntreeByIdProduitIdAgentPeriode(idProduit, idUser, startDate, endDate).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MouvementStockDto> sortieArticleUserPeriode(Long idProduit, Long idUser, Date startDate,
			Date endDate) {
		
		return mouvementStockRepository.findSortieByIdProduitIdAgentPeriode(idProduit, idUser, startDate, endDate).stream()
				.map(MouvementStockDto::fromEntity)
				.collect(Collectors.toList());
	}
	
	

}
