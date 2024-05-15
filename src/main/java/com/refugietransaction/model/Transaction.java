package com.refugietransaction.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractEntity {
	
	@Column(name = "transactioncode")
	private String transactionCode;
	
	@Column(name = "datetransaction")
	@Temporal(TemporalType.TIMESTAMP)
	@Convert(converter = LocalDateConverter.class)
	private LocalDate dateTransaction;
	
	@Column(name = "montanttransaction")
	private BigDecimal montantTransaction;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vente_id", nullable = false)
	private Ventes ventes;
}
