package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Transaction {

    public enum Status {
        PENDING("Pending"), PROCESSED("Processed");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Long id;
    private Status status;
    private String company;
    private String account;
    private Double amount;
    private boolean attachment;
    private LocalDate date;

    public Transaction(Long id, Status status, String company, String account, Double amount, boolean attachment, LocalDate date) {
        this.id = id;
        this.status = status;
        this.company = company;
        this.account = account;
        this.amount = amount;
        this.attachment = attachment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isAttachment() {
        return attachment;
    }

    public void setAttachment(boolean attachment) {
        this.attachment = attachment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
