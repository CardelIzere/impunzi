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
@Table(name = "mouvement_stock")

public class MouvementStock extends AbstractEntity {
	
	@Column(name = "date_mouvement")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateMouvement;
	
	@Column(name = "quantite")
	private BigDecimal quantite;
	
	@Column(name = "type_mouvement")
	@Enumerated(EnumType.STRING)
	private TypeMouvementStock typeMouvement;
	
	@ManyToOne
	@JoinColumn(name = "menage_id")
	@JsonIgnore
	private Menage menage;
	
	@ManyToOne 
	@JoinColumn(name = "id_article")
	private Produit produit;
	
	@ManyToOne 
	@JoinColumn(name = "user_id")
	private User user;
}
