package com.refugietransaction.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	
	@Column(name = "salecode")
	private String saleCode;
	
	@Column(name = "datevente")
	@Temporal(TemporalType.DATE)
	private LocalDate dateVente;
	
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
	
	@OneToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "ventes")
	private Transaction transaction; 
}
