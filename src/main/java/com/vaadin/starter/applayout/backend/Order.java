package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;
import java.util.Collection;

public class Order {

    private Status status;
    private Collection<Item> items;
    private String customer;
    private LocalDate date;

    public enum Status {
        ONGOING("Ongoing"), SENT("Sent"), FAILED("Failed");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Order(Status status, Collection<Item> items, String customer, LocalDate date) {
        this.status = status;
        this.items = items;
        this.customer = customer;
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public String getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }
}
