package com.refugietransaction.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "suppliers")
public class Supplier extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phonenumber")
	private String phoneNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "issupplieractive")
	private Boolean isSupplierActive;
	
	@OneToMany(mappedBy = "supplier")
	private List<Admin> admins;
	
	@OneToMany(mappedBy = "supplier")
	private List<Magasinier> magasiniers;
	
	@OneToMany(mappedBy = "supplier")
	private List<Product> products;
	
	@OneToMany(mappedBy = "supplier")
	private List<MvtStkSupplier> mouvementStockSupplier;
}
