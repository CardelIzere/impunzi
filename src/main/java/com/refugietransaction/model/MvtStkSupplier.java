package com.refugietransaction.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mvtstksupplier")

public class MvtStkSupplier extends AbstractEntity {
	
	@Column(name = "date_mouvement")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant dateMouvement;
	
	@Column(name = "quantite")
	private BigDecimal quantite;
	
	@Column(name = "type_mouvement")
	@Enumerated(EnumType.STRING)
	private TypeMvtStkSupplier typeMouvement;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@ManyToOne 
	@JoinColumn(name = "id_article")
	private Product produit;
	
	@ManyToOne
	@JoinColumn(name = "camp_id")
	private Camp camp;
	
}
