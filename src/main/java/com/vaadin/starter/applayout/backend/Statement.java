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
    private String payee;
    private Double output;
    private Double input;
    private Status status;
    private boolean attachment;

    public Statement(Long id, LocalDate date, String sender, Double output, Double input, Status status, boolean attachment) {
        this.id = id;
        this.date = date;
        this.payee = sender;
        this.output = output;
        this.input = input;
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

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Double getOutput() {
        return output;
    }

    public void setOutput(Double output) {
        this.output = output;
    }

    public Double getInput() {
        return input;
    }

    public void setInput(Double input) {
        this.input = input;
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
