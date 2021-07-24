package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.TKNgayThang;

public class ResponeGetDay {
    private TKNgayThang[] arrPitch;
    private String totalMoney;

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public TKNgayThang[] getArrPitch() {
        return arrPitch;
    }

    public void setArrPitch(TKNgayThang[] arrPitch) {
        this.arrPitch = arrPitch;
    }
}
