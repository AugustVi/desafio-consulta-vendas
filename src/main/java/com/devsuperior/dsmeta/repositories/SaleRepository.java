package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller " +
            "WHERE obj.date BETWEEN :initialDate AND :endDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
    Page<SaleMinDTO> searchBySale(Pageable pageable, LocalDate initialDate, LocalDate endDate, String name);

    @Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(amount) as TOTAL FROM tb_seller " +
            "INNER JOIN tb_sales ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :initialDate AND :endDate " +
            "GROUP BY tb_seller.name")
    Page<SummaryProjection> searchBySummary(Pageable pageable, LocalDate initialDate, LocalDate endDate);
}
