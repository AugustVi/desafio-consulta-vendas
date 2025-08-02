package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private SaleRepository repository;

	LocalDate parsedInitialDate;
	LocalDate parsedEndDate;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(Pageable pageable, String initialDate, String endDate, String name) {
		if(endDate == null || endDate.isEmpty()){
			parsedEndDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			parsedEndDate = LocalDate.parse(endDate, formatter);
		}

		if(initialDate == null || initialDate.isEmpty()) {
			parsedInitialDate = parsedEndDate.minusYears(1L);
		} else {
			parsedInitialDate = LocalDate.parse(initialDate, formatter);
		}
		return repository.searchBySale(pageable, parsedInitialDate, parsedEndDate, name);
	}

	public Page<SummaryMinDTO> getSummary(Pageable pageable, String initialDate, String endDate) {
		if(endDate == null || endDate.isEmpty()){
			parsedEndDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			parsedEndDate = LocalDate.parse(endDate, formatter);
		}

		if(initialDate == null || initialDate.isEmpty()) {
			parsedInitialDate = parsedEndDate.minusYears(1L);
		} else {
			parsedInitialDate = LocalDate.parse(initialDate, formatter);
		}
		Page<SummaryProjection> list = repository.searchBySummary(pageable, parsedInitialDate, parsedEndDate);
		return list.map(SummaryMinDTO::new);
	}
}
