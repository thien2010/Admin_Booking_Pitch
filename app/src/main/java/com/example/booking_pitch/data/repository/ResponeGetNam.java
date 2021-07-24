package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.TKNgayThang;
import com.example.booking_pitch.data.model.TkThang;

public class ResponeGetNam {
    private TKNgayThang[] arrPitch;
    private TkThang[] arrMonth;
    private String totalMoney;

    public TKNgayThang[] getArrPitch() {
        return arrPitch;
    }

    public void setArrPitch(TKNgayThang[] arrPitch) {
        this.arrPitch = arrPitch;
    }

    public TkThang[] getArrMonth() {
        return arrMonth;
    }

    public void setArrMonth(TkThang[] arrMonth) {
        this.arrMonth = arrMonth;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
