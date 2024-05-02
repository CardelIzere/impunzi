package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Camp;

public interface CampRepository extends JpaRepository<Camp, Long> {
	List<Camp> findAllById(Long id);
	// JPQL query
	  @Query(value = "select c from Camp c where c.nomCamp = :nomCamp")
	  Optional<Camp> findCampByName(@Param("nomCamp") String nomCamp);
}
