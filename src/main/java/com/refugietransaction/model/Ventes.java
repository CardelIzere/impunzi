package com.refugietransaction.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ventes")
public class Ventes extends AbstractEntity {
	
	@Column(name = "datevente")
	private Instant dateVente;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name = "camp_id")
	private Camp camp;
	
	@ManyToOne
	@JoinColumn(name = "menage_id")
	private Menage menage;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ventestatus")
	private VenteStatusEnum venteStatusEnum;
	
	@OneToMany(mappedBy = "vente")
	private List<LigneVente> ligneVentes;
}
