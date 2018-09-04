package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean originOfFunds;
    private int timesDenied;
    private LocalDate lastModified;

    public Person(int id, String firstName, String lastName, String email, boolean originOfFunds, int timesDenied, LocalDate lastModified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.originOfFunds = originOfFunds;
        this.timesDenied = timesDenied;
        this.lastModified = lastModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOriginOfFunds() {
        return originOfFunds;
    }

    public void setOriginOfFunds(boolean originOfFunds) {
        this.originOfFunds = originOfFunds;
    }

    public int getTimesDenied() {
        return timesDenied;
    }

    public void setTimesDenied(int timesDenied) {
        this.timesDenied = timesDenied;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getInitials() {
        return (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
    }
}
