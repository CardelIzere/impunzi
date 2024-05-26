package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	  @Query(value = "select p from Product p where p.nomProduit = :nomProduit")
	  Optional<Product> findProduitByNom(@Param("nomProduit") String nomProduit);
	  
	  @Query(value = "select p from Product p where p.id = :id")
	  Product findProductById(@Param("id") Long id);
	  
	  @Query(value = "select p from Product p order by p.id desc")
	  Page<Product> findAllProduits(Pageable pageable);
	  
	  @Query(value = "select p from Product p where UPPER(p.nomProduit) like CONCAT('%',UPPER(?1),'%') order by p.id desc ")
	  Page<Product> findByNameProduitLike(String search, Pageable pageable);
	  
	  List<Product> findAllByProductTypeId(Long product_type_id);
	  
	  @Query(value = "select p from Product p where p.supplier.id=?1 order by p.id desc")
	  Page<Product> findSupplierProducts(Long idSupplier, Pageable pageable);
	  
	  @Query(value = "select p from Product p where p.supplier.id=?1 AND UPPER(p.nomProduit) like CONCAT('%',UPPER(?2),'%') order by p.id desc")
	  Page<Product> findSupplierProductsByNomProduitLike(Long idSupplier, String search, Pageable pageable);
	  
	  @Query("select p from Product p where p.supplier.id = :idSupplier")
	  List<Product> findAllSupplierProducts(Long idSupplier);
	  
	  @Query("select p from Product p join p.ligneVente lv where lv.quantite > 0")
	  List<Product> findSoldProducts();

	List<Product> findAllById(Long id);
}
