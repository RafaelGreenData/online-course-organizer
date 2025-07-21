package com.oco.onlinecourseorganizer.dto;

public class RegisterDTO {

    private String email;
    private String password;

    // Default constructor
    public RegisterDTO() {
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
