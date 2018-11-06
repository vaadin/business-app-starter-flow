package com.vaadin.starter.responsiveapptemplate.backend;

import java.time.LocalDate;

public class Invoice {

    private final Long id;
    private final Status status;
    private final String customer;
    private final Double amount;
    private final LocalDate invoiceDate;
    private final LocalDate dueDate;

    public enum Status {
        OUTSTANDING("Pending"), OPEN("Open"), PAID("Sent"), OVERDUE("Failed");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Invoice(Long id, Status status, String customer, Double amount, LocalDate invoiceDate, LocalDate dueDate) {
        this.id = id;
        this.status = status;
        this.customer = customer;
        this.amount = amount;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getCustomer() {
        return customer;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
