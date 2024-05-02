package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

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

}
