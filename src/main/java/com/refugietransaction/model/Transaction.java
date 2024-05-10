package com.refugietransaction.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractEntity {
	
	@Column(name = "datetransaction")
	private Instant dateTransaction;
	
	@Column(name = "montanttransaction")
	private BigDecimal montantTransaction;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vente_id", nullable = false)
	private Ventes ventes;
}