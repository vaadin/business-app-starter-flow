package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Balance {

    private Long id;
    private final String bank;
    private final String account;
    private final String company;
    private final Double availability;
    private final LocalDate updated;

    public Balance(Long id, String bank, String account, String company, Double availability, LocalDate updated) {
        this.id = id;
        this.bank = bank;
        this.account = account;
        this.company = company;
        this.availability = availability;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }

    public String getCompany() {
        return company;
    }

    public Double getAvailability() {
        return availability;
    }

    public LocalDate getUpdated() {
        return updated;
    }
}
