package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;

public class Person {

    public enum Role {
        DESIGNER, DEVELOPER, MANAGER
    }

    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private String twitter;
    private int forumPosts;
    private LocalDate lastModified;

    public Person(Long id, String firstName, String lastName, Role role, String email, String twitter, int forumPosts, LocalDate lastModified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.twitter = twitter;
        this.forumPosts = forumPosts;
        this.lastModified = lastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public int getForumPosts() {
        return forumPosts;
    }

    public void setForumPosts(int forumPosts) {
        this.forumPosts = forumPosts;
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
