package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.TKNgayThang;

public class ResponeGetDay {
    private TKNgayThang[] arrPitch;
    private String totalMoney;
    private float totalCost;
    private String quantitySoccer;
    private String totalWater;
    private String totalUmpire;
    private String totalGiveUp;


    public String getTotalGiveUp() {
        return totalGiveUp;
    }

    public void setTotalGiveUp(String totalGiveUp) {
        this.totalGiveUp = totalGiveUp;
    }

    public String getTotalUmpire() {
        return totalUmpire;
    }

    public void setTotalUmpire(String totalUmpire) {
        this.totalUmpire = totalUmpire;
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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

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
