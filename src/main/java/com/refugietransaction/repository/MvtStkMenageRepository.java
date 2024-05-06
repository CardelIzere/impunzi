package com.refugietransaction.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Query("select m from MvtStkMenage m where m.typeMvtStkMenageEnum = 'RECEPTION' order by m.id desc ")
	Page<MvtStkMenage> findAllEntries(Pageable pageable);
	
	@Query("select m from MvtStkMenage m join ProductType pt on m.productType.id = pt.id join Menage me on m.menage.id = me.id where m.typeMvtStkMenageEnum = 'RECEPTION' And UPPER(pt.name) like CONCAT('%',UPPER(?1),'%') OR UPPER(me.personneContact) like CONCAT('%',UPPER(?1),'%') order by m.id desc ")
	Page<MvtStkMenage> findEntriesByProductTypeMenageLike(String search, Pageable pageable);
	
}
