package com.refugietransaction.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produit")
public class Produit extends AbstractEntity {
	
	@Column(name = "nom_produit")
	private String nomProduit;
	
	@OneToMany(mappedBy = "produit")
	private List<MouvementStock> mouvementStocks;
}
