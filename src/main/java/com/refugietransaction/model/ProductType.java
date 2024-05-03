package com.refugietransaction.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	@Column(name = "nameproducttype")
	private String nameProductTpe;
	
	@OneToMany(mappedBy = "productType")
	private List<Product> products;
}
