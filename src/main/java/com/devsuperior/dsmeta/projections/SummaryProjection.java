package com.devsuperior.dsmeta.projections;

import java.time.LocalDate;

public interface SummaryProjection {

    Long getId();
    Double getAmount();
    Double getTotal();
    LocalDate getDate();
    String getName();
}
