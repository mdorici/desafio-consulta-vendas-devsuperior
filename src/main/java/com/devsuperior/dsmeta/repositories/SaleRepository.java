package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT obj "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN :min AND :max "
			+ "AND UPPER(obj.seller.name) "
			+ "LIKE UPPER(CONCAT('%', :name, '%'))")
	Page<Sale> getReport(String name, LocalDate min, LocalDate max, Pageable pageable);

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN :min AND :max "
			+ "GROUP BY obj.seller.name")
	Page<SaleSummaryDTO> getSummary(LocalDate min, LocalDate max, Pageable pageable);
	
}
