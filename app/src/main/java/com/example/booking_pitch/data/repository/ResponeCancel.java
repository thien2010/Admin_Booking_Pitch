package com.example.booking_pitch.data.repository;

public class ResponeCancel {
    private String userID;
    private String codeSpecial;
    private String dateDone;
    private String dateCancel;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCodeSpecial() {
        return codeSpecial;
    }

    public void setCodeSpecial(String codeSpecial) {
        this.codeSpecial = codeSpecial;
    }

    public String getDateDone() {
        return dateDone;
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public String getDateCancel() {
        return dateCancel;
    }

    public void setDateCancel(String dateCancel) {
        this.dateCancel = dateCancel;
    }
}
