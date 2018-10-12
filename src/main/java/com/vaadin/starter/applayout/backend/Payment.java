package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Payment {

    private Status status;
    private String from;
    private String fromIBAN;
    private final String to;
    private final String toIBAN;
    private Double amount;
    private LocalDate date;

    public enum Status {
        PENDING("Pending"), OPEN("Open"), SENT("Sent"), FAILED("Failed");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Payment(Status status, String from, String fromIBAN, String to, String toIBAN, Double amount, LocalDate date) {
        this.status = status;
        this.from = from;
        this.fromIBAN = fromIBAN;
        this.to = to;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public String getFrom() {
        return from;
    }

    public String getFromIBAN() {
        return fromIBAN;
    }

    public String getTo() {
        return to;
    }

    public String getToIBAN() {
        return toIBAN;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
