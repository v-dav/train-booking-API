package org.example.trainplanet.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;


    // Default constructor
    public RegisterRequest() {}

    // Constructor with all fields
    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters for all fields
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email='" + email + '\'' +
                ", password='" + (password != null ? "[NON-NULL]" : "null") + '\'' +
                '}';
    }
}