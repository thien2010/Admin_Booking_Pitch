package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.SpanBusyClass;

import java.util.List;

public class ResponeSpanBusy {
    private SpanBusyClass[] data;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SpanBusyClass[] getData() {
        return data;
    }

    public void setData(SpanBusyClass[] data) {
        this.data = data;
    }
}
