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
@Table(name = "sales_unit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesUnit extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "salesUnit")
	private List<ProductType> productTypes;
	
}
