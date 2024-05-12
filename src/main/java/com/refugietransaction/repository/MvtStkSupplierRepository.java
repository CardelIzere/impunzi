package com.refugietransaction.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.MvtStkSupplier;

public interface MvtStkSupplierRepository extends JpaRepository<MvtStkSupplier, Long> {
	
	@Query("select m from MvtStkSupplier m where m.typeMouvement = 'ENTREE' order by m.id desc ")
	Page<MvtStkSupplier> findAllEntries(Pageable pageable);
	
	@Query("select m from MvtStkSupplier m join Product p on m.produit.id = p.id join Supplier s on m.supplier.id = s.id where m.camp.id = :idCamp And m.supplier.id = :idSupplier And m.typeMouvement = 'ENTREE' And (UPPER(p.nomProduit) like CONCAT('%',UPPER(:search),'%') OR UPPER(s.name) like CONCAT('%',UPPER(:search),'%')) order by m.id desc")
	Page<MvtStkSupplier> findByIdCampAndIdSupplierEntriesByNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable);
	
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
	
	List<MvtStkSupplier> findAllById(Long id);
	
	@Query("SELECT m.camp, m.produit, SUM(m.quantite) " +
			"FROM MvtStkSupplier m " +
			"WHERE m.supplier.id = :idSupplier " + 
			"GROUP BY m.camp, m.produit")
	List<Object[]> findTotalQuantityByIdSupplier(Long idSupplier);
	
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
