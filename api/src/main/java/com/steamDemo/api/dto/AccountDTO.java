package com.steamDemo.api.dto;

import java.util.UUID;

public class AccountDTO {
    private UUID userId;
    private String userName;
    private String country;
    private String email;


    public AccountDTO(UUID userId, String userName, String country, String email, String accountDescription) {
        this.userId = userId;
        this.userName = userName;
        this.country = country;
        this.email = email;

    }

    // Getters e Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
