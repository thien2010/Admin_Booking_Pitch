package com.example.booking_pitch.data.model;

public class AddPitch {
    private String pitchID;
    private String pitchName;
    private String price;
    private String priceWater;
    private String image;
    private String detail;
    private String createBy;
    private String _id;
    private boolean success;
    private String message;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPitchID() {
        return pitchID;
    }

    public void setPitchID(String pitchID) {
        this.pitchID = pitchID;
    }

    public String getPitchName() {
        return pitchName;
    }

    public void setPitchName(String pitchName) {
        this.pitchName = pitchName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceWater() {
        return priceWater;
    }

    public void setPriceWater(String priceWater) {
        this.priceWater = priceWater;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
