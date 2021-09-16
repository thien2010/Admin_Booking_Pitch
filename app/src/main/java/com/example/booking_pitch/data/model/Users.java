package com.example.booking_pitch.data.model;

public class Users {
    private String userName;
    private String quantityCancel;
    private String quantityWating;
    private String quantityKeeping;
    private String quantityDone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuantityCancel() {
        return quantityCancel;
    }

    public void setQuantityCancel(String quantityCancel) {
        this.quantityCancel = quantityCancel;
    }

    public String getQuantityWating() {
        return quantityWating;
    }

    public void setQuantityWating(String quantityWating) {
        this.quantityWating = quantityWating;
    }

    public String getQuantityKeeping() {
        return quantityKeeping;
    }

    public void setQuantityKeeping(String quantityKeeping) {
        this.quantityKeeping = quantityKeeping;
    }

    public String getQuantityDone() {
        return quantityDone;
    }

    public void setQuantityDone(String quantityDone) {
        this.quantityDone = quantityDone;
    }
}
