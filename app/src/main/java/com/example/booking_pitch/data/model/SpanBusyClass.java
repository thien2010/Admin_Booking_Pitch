package com.example.booking_pitch.data.model;

import java.util.List;

public class SpanBusyClass {
    private String pitchName;
    private List<String> spanBusy;
    private String date;

    public void setPitchName(String pitchName) {
        this.pitchName = pitchName;
    }

    public void setSpanBusy(List<String> spanBusy) {
        this.spanBusy = spanBusy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPitchName() {
        return pitchName;
    }

    public List<String> getSpanBusy() {
        return spanBusy;
    }
}
