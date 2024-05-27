package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Menage;

public interface MenageRepository extends JpaRepository<Menage, Long> {

	List<Menage> findAllById(Long id);
	// JPQL query
	@Query(value = "select m from Menage m where m.idNumber = :idNumber")
	Optional<Menage> findMenageByIdNumber(@Param("idNumber") Long idNumber);
	
	@Query(value = "select m from Menage m where m.id = :id")
	Menage findMenageById(@Param("id") Long id);
	
	@Query(value = "select m from Menage m where m.numTelephone = :numTelephone")
	Optional<Menage> findMenageByPhoneNumber(@Param("numTelephone") String numTelephone);
	
	@Query(value = "select m from Menage m order by m.id desc")
	Page<Menage> findAllMenages(Pageable pageable);
	
	@Query(value = "select m from Menage m join Camp c on m.camp.id = c.id where CAST(m.idNumber AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(m.personneContact) like LOWER(CONCAT('%', :search, '%')) OR LOWER(m.numTelephone) like LOWER(CONCAT('%', :search, '%')) OR LOWER(m.langueParlee) like LOWER(CONCAT('%', :search, '%')) OR LOWER(c.nomCamp) like LOWER(CONCAT('%', :search, '%')) order by m.id desc ")
	Page<Menage> findByPersonneContactNumTeleLike(String search, Pageable pageable);
	
	List<Menage> findAllByCamp_Id(Long camp_id);
	
	@Query("SELECT m FROM Menage m WHERE " +
			"m.camp.id = :idCamp And " +
			"CAST(m.idNumber AS string) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
			"LOWER(m.personneContact) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
			"LOWER(m.numTelephone) LIKE LOWER(CONCAT('%', :searchString, '%'))")
	List<Menage> findAllByCampIdByPersonneContactNumTeleIdNumberLike(Long idCamp, String searchString);

}
