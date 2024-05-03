package com.refugietransaction.model;

import java.util.List;
import java.util.Set;

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
@Table(name = "CAMP_TBL")
public class Camp extends AbstractEntity {
	
	@Column(name = "nom_camp")
	private String nomCamp;
	
	@Column(name = "addresscamp")
	private String addressCamp;
	
	@OneToMany(mappedBy = "camp")
	private List<Menage> menages;
	
	


}
