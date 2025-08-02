package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryProjection;

public class SummaryMinDTO {

    private String sellerName;
    private Double total;

    public SummaryMinDTO() {}

    public SummaryMinDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SummaryMinDTO(Sale entity) {
        sellerName = entity.getSeller().getName();
        total = entity.getAmount();
    }

    public SummaryMinDTO(SummaryProjection projection) {
        sellerName = projection.getName();
        total = projection.getTotal();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
