package com.example.booking_pitch.data.model;

public class LoginAdminAccount {
    private String username;
    private String password;
    private boolean success;

    public LoginAdminAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

