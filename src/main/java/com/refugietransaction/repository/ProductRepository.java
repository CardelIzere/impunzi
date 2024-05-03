package com.refugietransaction.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	// JPQL query
	  @Query(value = "select p from Product p where p.nomProduit = :nomProduit")
	  Optional<Product> findProduitByNom(@Param("nomProduit") String nomProduit);
	  
	  @Query(value = "select p from Product p order by p.id desc")
	  Page<Product> findAllProduits(Pageable pageable);
	  
	  @Query(value = "select p from Product p where UPPER(p.nomProduit) like CONCAT('%',UPPER(?1),'%') order by p.id desc ")
	  Page<Product> findByNameProduitLike(String search, Pageable pageable);
}
