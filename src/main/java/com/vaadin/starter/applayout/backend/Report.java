package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Report {

    private Long id;
    private String source;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double balance;
    private String currency;

    public Report(Long id, String source, String name, LocalDate startDate, LocalDate endDate, Double balance, String currency) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.balance = balance;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

}
