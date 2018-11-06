package com.vaadin.starter.responsiveapptemplate.backend;

import java.time.LocalDate;
import java.util.Collection;

public class Order {

    private Status status;
    private Collection<Item> items;
    private String customer;
    private LocalDate date;
    private Double value;

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

    public Order(Status status, Collection<Item> items, String customer, LocalDate date) {
        this.status = status;
        this.items = items;
        this.customer = customer;
        this.date = date;
        this.value = 0.0;

        for (Item item : items) {
            this.value += item.getPrice();
        }
    }

    public Status getStatus() {
        return status;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

    public String getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return this.value;
    }
}
