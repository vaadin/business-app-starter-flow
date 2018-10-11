package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Payment {

    private Status status;
    private String bank;
    private String account;
    private String payer;
    private LocalDate date;
    private Double amount;

    public enum Status {
        SENT("Sent"), OPEN("Open"), PENDING("Pending");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Payment(Status status, String bank, String account, String payer, LocalDate date, Double amount) {
        this.status = status;
        this.bank = bank;
        this.account = account;
        this.payer = payer;
        this.date = date;
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }

    public String getPayer() {
        return payer;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

}
