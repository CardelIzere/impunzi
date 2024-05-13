package com.refugietransaction.model;

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

@Entity
@Table(name = "producttype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductType extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "salesunit_id")
	private SalesUnit salesUnit;
	
	@OneToMany(mappedBy = "productType")
	private List<Product> products;
	
	@OneToMany(mappedBy = "productType")
	private List<MvtStkMenage> mvtStkMenage;
}
