package com.refugietransaction.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refugietransaction.model.MvtStkMenage;

public interface MvtStkMenageRepository extends JpaRepository<MvtStkMenage, Long> {
	
	@Query("select sum(m.quantite) from MvtStkMenage m where m.productType.id = :idProductType and m.menage.id = :idMenage")
	BigDecimal stockReelMenage(@Param("idProductType") Long idProductType, @Param("idMenage") Long idMenage);
	
	@Query("select m from MvtStkMenage m where m.productType.id = :idProductType and m.menage.id = :idMenage")
	List<MvtStkMenage> findAllByProductTypeIdAndMenageId(@Param("idProductType") Long idProductType, @Param("idMenage") Long idMenage);
	
	List<MvtStkMenage> findAllById(Long id);
	
	
}
