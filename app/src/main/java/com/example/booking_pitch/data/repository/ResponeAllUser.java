package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.Users;

public class ResponeAllUser {
    private Users[] data;
    private String quantityUser;
    private boolean success;

    public Users[] getData() {
        return data;
    }

    public void setData(Users[] data) {
        this.data = data;
    }

    public String getQuantityUser() {
        return quantityUser;
    }

    public void setQuantityUser(String quantityUser) {
        this.quantityUser = quantityUser;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
