package com.example.demo2.controllers;

public class JwtResponse {
    private String token;
    private String type = "Bearer";  // This is often used as the authentication scheme prefix for JWTs

    // Optional: include additional details as needed
    private String username;

    // Constructors
    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
