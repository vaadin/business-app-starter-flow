package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Statement {

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
    private LocalDate date;
    private String sender;
    private Double amount;
    private Status status;
    private boolean attachment;

    public Statement(Long id, LocalDate date, String sender, Double amount, Status status, boolean attachment) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.amount = amount;
        this.status = status;
        this.attachment = attachment;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean hasAttachment() {
        return attachment;
    }

    public void setAttachment(boolean attachment) {
        this.attachment = attachment;
    }
}
