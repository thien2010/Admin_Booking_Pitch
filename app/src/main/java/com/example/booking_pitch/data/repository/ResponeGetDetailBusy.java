package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.PitchClass;

public class ResponeGetDetailBusy {
    private PitchClass[] data;
    private boolean success;
    private String mesage;

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public PitchClass[] getData() {
        return data;
    }

    public void setData(PitchClass[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
