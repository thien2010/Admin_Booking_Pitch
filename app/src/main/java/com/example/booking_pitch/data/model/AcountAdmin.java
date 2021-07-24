package com.example.booking_pitch.data.model;

public class AcountAdmin {
    private String username;
    private String password;
    private boolean success;
    private String message;

    public AcountAdmin(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
