package com.refugietransaction.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Product extends AbstractEntity {
	
	@Column(name = "nom_produit")
	private String nomProduit;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@OneToMany(mappedBy = "produit")
	private List<MvtStkSupplier> mouvementStockSupplier;
	
	@ManyToOne
	@JoinColumn(name = "product_type_id")
	private ProductType productType;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
}
