package com.vaadin.starter.responsivelayoutgrid.backend;

import java.time.LocalDate;

public class InitialCoinOffering {

    private Long id;
    private String source;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double amountRaised;

    public InitialCoinOffering(Long id, String source, String name, LocalDate startDate, LocalDate endDate, Double amountRaised) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amountRaised = amountRaised;
    }

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setLogo(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getAmountRaised() {
        return amountRaised;
    }

    public void setAmountRaised(Double amountRaised) {
        this.amountRaised = amountRaised;
    }

}
