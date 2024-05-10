package com.refugietransaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refugietransaction.model.LigneVente;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

	List<LigneVente> findAllByVenteId(Long id);
	
	@Query("select lv from LigneVente lv where lv.id = :id")
	LigneVente findLigneVenteById(Long id);
	
	@Query("SELECT lv FROM LigneVente lv  JOIN Product p on lv.product.id = p.id where lv.vente.id = :idVente")
	List<LigneVente> findSoldProductsInAllSales(Long idVente);

}
