package com.refugietransaction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends AbstractEntity {
	//Admin model
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "admintype")
	private AdminTypeEnum adminTypeEnum;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
}
