package com.refugietransaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.Camp;

public interface CampRepository extends JpaRepository<Camp, Long> {
	List<Camp> findAllById(Long id);
	// JPQL query
	  @Query(value = "select c from Camp c where c.nomCamp = :nomCamp")
	  Optional<Camp> findCampByName(@Param("nomCamp") String nomCamp);
	  
	  @Query(value = "select c from Camp c order by c.id desc")
	  Page<Camp> findAllCamps(Pageable pageable);
	  
	  @Query(value = "select c from Camp c where UPPER(c.nomCamp) like CONCAT('%', UPPER(?1), '%') OR UPPER(c.addressCamp) like CONCAT('%',UPPER(?1),'%') order by c.id desc ")
	  Page<Camp> findByNameCampAddressLike(String search, Pageable pageable);
}
