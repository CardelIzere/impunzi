package com.refugietransaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refugietransaction.model.LigneVente;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

	List<LigneVente> findAllByVenteId(Long id);

}
