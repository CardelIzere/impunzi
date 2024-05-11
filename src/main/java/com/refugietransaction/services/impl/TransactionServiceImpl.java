package com.refugietransaction.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.TransactionDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.LigneVente;
import com.refugietransaction.model.Transaction;
import com.refugietransaction.model.VenteStatusEnum;
import com.refugietransaction.model.Ventes;
import com.refugietransaction.repository.LigneVenteRepository;
import com.refugietransaction.repository.TransactionRepository;
import com.refugietransaction.repository.VentesRepository;
import com.refugietransaction.services.TransactionService;
import com.refugietransaction.validator.TransactionValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final VentesRepository ventesRepository;
	private final LigneVenteRepository ligneVenteRepository;
	
	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, VentesRepository ventesRepository, LigneVenteRepository ligneVenteRepository) {
		this.transactionRepository = transactionRepository;
		this.ventesRepository = ventesRepository;
		this.ligneVenteRepository = ligneVenteRepository;
	}
	
	@Override
	public TransactionDto save(TransactionDto dto) {
		
		List<String> errors = TransactionValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Transaction is not valid {}", dto);
			throw new InvalidEntityException("L'objet Transaction n'est pas valide", ErrorCodes.TRANSACTION_NOT_VALID, errors);
		}
		
		Ventes ventes = ventesRepository.findVentesById(dto.getVentes().getId());
		
		if(ventes.getVenteStatusEnum() == VenteStatusEnum.PAID) {
			throw new InvalidEntityException("La vente a été deja payé", ErrorCodes.VENTE_ALREADY_PAID,
					Collections.singletonList("La transaction impossible car la vente a été deja payé"));
		}
		
		BigDecimal montantTransaction = BigDecimal.ZERO;
		for(LigneVente ligneVente : ventes.getLigneVentes()) {
			BigDecimal prixUnitaire = ligneVente.getPrixUnitaire();
			
			montantTransaction = montantTransaction.add(prixUnitaire);
		}
		
		Transaction transaction = new Transaction();
		transaction.setDateTransaction(Instant.now());
		transaction.setMontantTransaction(montantTransaction);
		transaction.setVentes(ventes);
		
		//Save Transaction and update Vente status in one transaction
		ventes.setVenteStatusEnum(VenteStatusEnum.PAID);
		ventes.setTransaction(transaction);
		ventesRepository.save(ventes);
		
		return TransactionDto.fromEntity(transaction);
	}

	@Override
	public TransactionDto findById(Long id) {
		if(id == null) {
			log.error("Transaction ID is null");
		}
		return transactionRepository.findById(id)
				.map(TransactionDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucune transaction avec l'ID = " +id+ " n'a ete trouve dans la BDD", 
						ErrorCodes.TRANSACTION_NOT_FOUND));
	}

	@Override
	public List<TransactionDto> findAll() {
		
		return transactionRepository.findAll().stream()
				.map(TransactionDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long idTransaction) {
		if(idTransaction == null) {
			log.error("Transaction ID is null");
		}
		
		transactionRepository.deleteById(idTransaction);
		
	}

	@Override
	public Page<TransactionDto> findCampSupplierTransactionByPersonneContactLike(Long idCamp, Long idSupplier,
			String search, Pageable pageable) {
		Page<Transaction> transactions;
		if(search != null) {
			transactions = transactionRepository.findCampSupplierTransactionByIdNumberLike(idCamp, idSupplier, search, pageable);
		} else {
			transactions = transactionRepository.findAllTransactions(pageable);
		}
		return transactions.map(TransactionDto::fromEntity);
	}

	@Override
	public Page<TransactionDto> findSupplierTransactionByIdNumberLike(Long idSupplier, String search,
			Pageable pageable) {
		Page<Transaction> transactions;
		if(search != null) {
			transactions = transactionRepository.findSupplierTransactionByIdNumberLike(idSupplier, search, pageable);
		} else {
			transactions = transactionRepository.findAllTransactions(pageable);
		}
		return transactions.map(TransactionDto::fromEntity);
	}

}
