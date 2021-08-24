package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.TKNgayThang;
import com.example.booking_pitch.data.model.TkThang;

public class ResponeGetNam {
    private TKNgayThang[] arrPitch;
    private TkThang[] arrMonth;
    private String totalMoney;
    private float totalCost;
    private String quantitySoccer;
    private String totalWater;
    private String totalUmpire;

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getQuantitySoccer() {
        return quantitySoccer;
    }

    public void setQuantitySoccer(String quantitySoccer) {
        this.quantitySoccer = quantitySoccer;
    }

    public String getTotalWater() {
        return totalWater;
    }

    public void setTotalWater(String totalWater) {
        this.totalWater = totalWater;
    }

    public String getTotalUmpire() {
        return totalUmpire;
    }

    public void setTotalUmpire(String totalUmpire) {
        this.totalUmpire = totalUmpire;
    }

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
