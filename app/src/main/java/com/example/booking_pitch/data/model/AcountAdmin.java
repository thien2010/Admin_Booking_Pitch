package com.example.booking_pitch.data.model;

public class AcountAdmin {
    private String username;
    private String password;
    private String userID;
    private String oldPassword;
    private String newPassword;
    private boolean success;
    private String message;

    public AcountAdmin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AcountAdmin(String userID, String oldPassword, String newPassword) {
        this.username = userID;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
