package com.refugietransaction.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Camp;
import com.refugietransaction.model.MvtStkSupplier;
import com.refugietransaction.model.Ventes;
import com.refugietransaction.projections.ByCampStockProjection;

public interface MvtStkSupplierRepository extends JpaRepository<MvtStkSupplier, Long> {
	
	//Entries
	@Query("select m from MvtStkSupplier m where m.typeMouvement = 'ENTREE' order by m.id desc ")
	Page<MvtStkSupplier> findAllEntries(Pageable pageable);
	
	//Entries by camp_id and supplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.camp.id = :idCamp And m.supplier.id = :idSupplier And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdCampAndIdSupplierEntriesByNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findBySupplierIdCampIdEntries(Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndSupplierIdCampId(LocalDate startDate, LocalDate endDate, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndSearchAndSupplierIdCampId(LocalDate startDate ,LocalDate endDate, Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	//Entries by supplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdSupplierEntriesByNameLike(Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findBySupplierIdEntries(Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndSupplierId(LocalDate startDate, LocalDate endDate, Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndSearchAndSupplierId(LocalDate startDate ,LocalDate endDate, Long idSupplier, String search, Pageable pageable);
	
	@Query("select sum(m.quantite) from  MvtStkSupplier m where m.produit.id = :idProduit and m.supplier.id = :idSupplier")
	BigDecimal stockReelSupplier(@Param("idProduit") Long idProduit, @Param("idSupplier") Long idSupplier);
	
	@Query("select m from  MvtStkSupplier m where m.produit.id = :idProduit and m.supplier.id = :idSupplier")
	List<MvtStkSupplier> findAllByArticleIdAndSupplierId(@Param("idProduit") Long idProduit, @Param("idSupplier") Long idSupplier);
	
	@Query("select m from MvtStkSupplier m where m.supplier.id = :idSupplier and m.camp.id = :idCamp order by m.id desc")
	List<MvtStkSupplier> findMvtStkSupplierBySupplierAndCamp(Long idSupplier, Long idCamp);
	
	@Query("select m from MvtStkSupplier m where m.produit.id = :idProduit order by m.id desc")
	List<MvtStkSupplier> findMvtStkSupplierByProductId(Long idProduit);
	
	@Query("select m from MvtStkSupplier m order by m.id desc")
	Page<MvtStkSupplier> findAllMvtStkSuppliers(Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where (UPPER(p.nomProduit) like CONCAT('%',UPPER(?1),'%') OR UPPER(s.name) like CONCAT('%',UPPER(?1),'%')) order by m.id desc")
	Page<MvtStkSupplier> findAllByProductSupplierLike(String search, Pageable pageable);
	
	//Sorties
	@Query("select m from MvtStkSupplier m where m.typeMouvement = 'SORTIE' order by m.id desc ")
	Page<MvtStkSupplier> findAllSorties(Pageable pageable);
	
	//Sorties by camp_id and suuplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.camp.id = :idCamp And m.supplier.id = :idSupplier And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdCampAndIdSupplierSortiesByNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findBySupplierIdCampIdSorties(Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndSupplierIdCampId(LocalDate startDate, LocalDate endDate, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndSearchAndSupplierIdCampId(LocalDate startDate ,LocalDate endDate, Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	//Sorties by supplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdSupplierSortiesByNameLike(Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findBySupplierIdSorties(Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndSupplierId(LocalDate startDate, LocalDate endDate, Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndSearchAndSupplierId(LocalDate startDate ,LocalDate endDate, Long idSupplier, String search, Pageable pageable);
	
	//Entries by product_id, camp_id and supplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.camp.id = :idCamp And m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdProductIdCampAndIdSupplierEntriesByNameLike(Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findByProductIdSupplierIdCampIdEntries(Long idProduct, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndProductIdSupplierIdCampId(LocalDate startDate, LocalDate endDate, Long idProduct, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndSearchAndProductIdSupplierIdCampId(LocalDate startDate ,LocalDate endDate, Long idProduct, Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	//Entries by product_id and supplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdProductIdSupplierEntriesByNameLike(Long idProduct, Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findByProductIdSupplierIdEntries(Long idProduct, Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndProductIdSupplierId(LocalDate startDate, LocalDate endDate, Long idProduct, Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findEntriesByStartDateAndEndDateAndSearchAndProductIdSupplierId(LocalDate startDate ,LocalDate endDate, Long idProduct, Long idSupplier, String search, Pageable pageable);
	
	
	//Sorties by product_id, camp_id and supplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.camp.id = :idCamp And m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdProductIdCampAndIdSupplierSortiesByNameLike(Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findByProductIdSupplierIdCampIdSorties(Long idProduct, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndProductIdSupplierIdCampId(LocalDate startDate, LocalDate endDate, Long idProduct, Long idSupplier, Long idCamp, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.camp.id = :idCamp And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndSearchAndProductIdSupplierIdCampId(LocalDate startDate ,LocalDate endDate, Long idProduct, Long idSupplier, Long idCamp, String search, Pageable pageable);
	
	
	//Sorties by product_id and supplier_id
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdProductIdSupplierSortiesByNameLike(Long idProduct, Long idSupplier, String search, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findByProductIdSupplierIdSorties(Long idProduct, Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndProductIdSupplierId(LocalDate startDate, LocalDate endDate, Long idProduct, Long idSupplier, Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.dateMouvement BETWEEN :startDate AND :endDate AND m.supplier.id = :idSupplier And m.produit.id = :idProduct And m.typeMouvement = 'SORTIE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findSortiesByStartDateAndEndDateAndSearchAndProductIdSupplierId(LocalDate startDate ,LocalDate endDate, Long idProduct, Long idSupplier, String search, Pageable pageable);
	
	List<MvtStkSupplier> findAllById(Long id);
	
	@Query("SELECT ms.produit, SUM(ms.quantite) " +
			"FROM MvtStkSupplier ms " +
			"WHERE ms.camp = :camp " +
			"AND ms.supplier.id = :supplierId " +
			"GROUP BY ms.produit")
	List<Object[]> findStockQuantityByCamp(@Param("supplierId") Long supplierId,Camp camp);

	@Query("SELECT DISTINCT m.camp FROM MvtStkSupplier m WHERE m.supplier.id = :supplierId")
	List<Camp> findDistinctCampsBySupplierId(@Param("supplierId") Long supplierId);
	
	
	@Query("SELECT c.nomCamp, ms.produit, SUM(ms.quantite) " +
			"FROM MvtStkSupplier ms " +
			"JOIN Camp c ON ms.camp.id = c.id " +
			"WHERE ms.produit.id = :productId " +
			"GROUP BY c.nomCamp, ms.produit")
	List<Object[]> findProductStockQuantityByCamp(@Param("productId") Long productId);

	@Query("SELECT DISTINCT m.camp FROM MvtStkSupplier m WHERE m.produit.id = :productId")
	List<Camp> findDistinctCampsByProductId(@Param("productId") Long productId);
	
	@Query("SELECT p.id AS productId, p.nomProduit AS nomProduit, p.price AS price, COALESCE(SUM(m.quantite), 0) AS inStockQuantity " +
			"FROM Product p " +
			"LEFT JOIN MvtStkSupplier m ON p.id = m.produit.id AND m.camp.id = :idCamp AND m.supplier.id = :idSupplier " +
			"WHERE UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') " +
			"GROUP BY p.id, p.nomProduit, p.price")
	Page<ByCampStockProjection> findTotalQuantityByIdCampIdSupplierAndNameProductLike(@Param("idCamp") Long idCamp, @Param("idSupplier") Long idSupplier, String search, Pageable pageable);
	
	@Query("SELECT p.id AS productId, p.nomProduit AS nomProduit, pt.name AS nomProductType, p.price AS price, SUM(m.quantite) AS inStockQuantity, s.name AS salesName " +
			"FROM Product p " +
			"LEFT JOIN MvtStkSupplier m ON p.id = m.produit.id AND m.camp.id = :idCamp AND m.supplier.id = :idSupplier " +
			"JOIN ProductType pt ON p.productType.id = pt.id " +
			"JOIN SalesUnit s ON pt.salesUnit.id = s.id " +
			"GROUP BY p.id, p.nomProduit, pt.name, p.price, s.name")
	Page<ByCampStockProjection> findTotalQuantityByIdCampIdSupplier(@Param("idCamp") Long idCamp, @Param("idSupplier") Long idSupplier, Pageable pageable);
	
	@Query("SELECT ms " +
			"FROM MvtStkSupplier ms " +
			"WHERE ms.dateMouvement BETWEEN :startDate AND :endDate " +
			"AND ms.produit.id = :idProduct " +
			"AND ms.supplier.id = :idSupplier " +
			"AND ms.camp.id = :idCamp " +
			"order by ms.id desc")
	Page<MvtStkSupplier> findProductMvtStkBySupplierCampWithDate(LocalDate startDate, LocalDate endDate, @Param("idProduct") Long idProduct, @Param("idSupplier") Long idSupplier, @Param("idCamp") Long idCamp, Pageable pageable);
	
	@Query("SELECT ms " +
			"FROM MvtStkSupplier ms " +
			"WHERE ms.produit.id = :idProduct " +
			"AND ms.supplier.id = :idSupplier " +
			"AND ms.camp.id = :idCamp " +
			"order by ms.id desc")
	Page<MvtStkSupplier> findProductMvtStkBySupplierCamp(@Param("idProduct") Long idProduct, @Param("idSupplier") Long idSupplier, @Param("idCamp") Long idCamp, Pageable pageable);
	
	
	
	//Liste des entrees de stock d'un produit donné dans un camp donné
//	@Query("select m from MouvementStock m join User u on m.user.id = u.id join UserAssignment ua on u.id = ua.utilisateur.id join Camp c on ua.camp.id = c.id where m.produit.id = :idProduit AND c.id = :idCamp AND m.typeMouvement = 'ENTREE' ")
//	List<MouvementStock> findEntreeByIdProduitIdCamp(@Param("idProduit") Long idProduit, @Param("idCamp") Long idCamp);
	
	//Liste des sorties de stock d'un produit donné dans un camp donné
//	@Query("select m from MouvementStock m join Utilisateur u on m.utilisateur.id = u.id join UserAssignment ua on u.id = ua.utilisateur.id join Camp c on ua.camp.id = c.id where m.produit.id = :idProduit AND c.id = :idCamp AND m.typeMouvement = 'SORTIE' ")
//	List<MouvementStock> findSortieByIdProduitIdCamp(@Param("idProduit") Long idProduit, @Param("idCamp") Long idCamp);
	
	//Liste des entrees de stock d'un produit donné dans un camp donné pour une periode donnée
//	@Query("select m from MouvementStock m join Utilisateur u on m.utilisateur.id = u.id join UserAssignment ua on u.id = ua.utilisateur.id join Camp c on ua.camp.id = c.id where m.produit.id = :idProduit AND c.id = :idCamp AND m.typeMouvement = 'ENTREE' AND m.dateMouvement BETWEEN :startDate AND :endDate ")
//	List<MouvementStock> findEntreeByIdProduitIdCampPeriode(@Param("idProduit") Long idProduit, @Param("idCamp") Long idCamp, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	//Liste des sorties de stock d'un produit donné dans un camp donné pour une periode donné
//	@Query("select m from MouvementStock m join Utilisateur u on m.utilisateur.id = u.id join UserAssignment ua on u.id = ua.utilisateur.id join Camp c on ua.camp.id = c.id where m.produit.id = :idProduit AND c.id = :idCamp AND m.typeMouvement = 'SORTIE' AND m.dateMouvement BETWEEN :startDate AND :endDate ")
//	List<MouvementStock> findSortieByIdProduitIdCampPeriode(@Param("idProduit") Long idProduit, @Param("idCamp") Long idCamp, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	//Liste des entrees de stock d'un produit donné pour des fournisseurs
	@Query("select m from MvtStkSupplier m where m.produit.id = :idProduit AND m.supplier.id = :idSupplier AND m.typeMouvement = 'ENTREE' ")
	List<MvtStkSupplier> findEntreeByIdProduitIdSupplier(@Param("idProduit") Long idProduit, @Param("idSupplier") Long idSupplier);
	
	//Liste des sorties de stock d'un produit donné dans un menage donné
	@Query("select m from MvtStkSupplier m where m.produit.id = :idProduit AND m.supplier.id = :idSupplier AND m.typeMouvement = 'SORTIE' ")
	List<MvtStkSupplier> findSortieByIdProduitIdSupplier(@Param("idProduit") Long idProduit, @Param("idSupplier") Long idSupplier);
	
	//Liste des entrees de stock d'un produit donné dans un menage donné pour une periode donné
	@Query("select m from MvtStkSupplier m where m.produit.id = :idProduit AND m.supplier.id = :idSupplier AND m.typeMouvement = 'ENTREE' AND m.dateMouvement BETWEEN :startDate AND :endDate ")
	List<MvtStkSupplier> findEntreeByIdProduitIdSupplierPeriode(@Param("idProduit") Long idProduit, @Param("idSupplier") Long idSupplier, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	//Liste des sorties de stock d'un produit donné dans un menage donné pour une periode donné
	@Query("select m from MvtStkSupplier m where m.produit.id = :idProduit AND m.supplier.id = :idSupplier AND m.typeMouvement = 'SORTIE' AND m.dateMouvement BETWEEN :startDate AND :endDate ")
	List<MvtStkSupplier> findSortieByIdProduitIdSupplierPeriode(@Param("idProduit") Long idProduit, @Param("idSupplier") Long idMenage, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
//	
//	//Liste des entrees de stock d'un produit donné effectuées par un utilisateur donné
//	@Query("select m from MouvementStockSupplier m where m.produit.id = :idProduit AND m.user.id = :idUser AND m.typeMouvement = 'ENTREE' ")
//	List<MvtStkSupplier> findEntreeByIdProduitIdAgent(@Param("idProduit") Long idProduit, @Param("idUser") Long idUser);
//		
//	//Liste des sorties de stock d'un produit donné effectuées par un agent donné
//	@Query("select m from MouvementStock m where m.produit.id = :idProduit AND m.user.id =:idUser AND m.typeMouvement = 'SORTIE' ")
//	List<MvtStkSupplier> findSortieByIdProduitIdAgent(@Param("idProduit") Long idProduit, @Param("idUser") Long idUser);
//		
//	//Liste des entrees de stock d'un produit donné effectuées par un agent donné pour une poriode donnée
//	@Query("select m from MouvementStock m where m.produit.id = :idProduit AND m.user.id = :idUser AND m.typeMouvement = 'ENTREE' AND m.dateMouvement BETWEEN :startDate AND :endDate ")
//	List<MvtStkSupplier> findEntreeByIdProduitIdAgentPeriode(@Param("idProduit") Long idProduit, @Param("idUser") Long idUser, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
//		
//	//Liste des sorties de stock d'un produit donné effectuées par un agent donné pour une periode donnée
//	@Query("select m from MouvementStock m where m.produit.id = :idProduit AND m.user.id = :idUser AND m.typeMouvement = 'SORTIE' AND m.dateMouvement BETWEEN :startDate AND :endDate ")
//	List<MvtStkSupplier> findSortieByIdProduitIdAgentPeriode(@Param("idProduit") Long idProduit, @Param("idUser") Long idUser, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
//	
//	
	
}
