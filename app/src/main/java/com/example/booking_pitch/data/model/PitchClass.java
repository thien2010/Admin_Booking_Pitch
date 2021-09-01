package com.example.booking_pitch.data.model;

public class PitchClass {
    private String pitchID;
    private String pitchName;
    private String span;
    private String userID;
    private String date;
    private String state;
    private String totalPrice;
    private String price;
    private boolean umpire;
    private String quantityWater;
    private String priceWater;
    private String image;
    private boolean tshirt;
    private String detail;
    private String userName;
    private String createBy;
    private String _id;

    public PitchClass() {
    }

    public PitchClass(String pitchName, String price, String image, String detail) {
        this.pitchName = pitchName;
        this.price = price;
        this.image = image;
        this.detail = detail;
    }

    public PitchClass(String pitchID, String pitchName, String span, String userID, String date, String state, String totalPrice, String price, boolean umpire, String quantityWater, String priceWater, String image, boolean tshirt, String detail, String createBy) {
        this.pitchID = pitchID;
        this.pitchName = pitchName;
        this.span = span;
        this.userID = userID;
        this.date = date;
        this.state = state;
        this.totalPrice = totalPrice;
        this.price = price;
        this.umpire = umpire;
        this.quantityWater = quantityWater;
        this.priceWater = priceWater;
        this.image = image;
        this.tshirt = tshirt;
        this.detail = detail;
        this.createBy = createBy;
    }

    public PitchClass(String _id, String state) {
        this.state = state;
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public PitchClass(String state) {
        this.state = state;
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

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isUmpire() {
        return umpire;
    }

    public void setUmpire(boolean umpire) {
        this.umpire = umpire;
    }

    public String getQuantityWater() {
        return quantityWater;
    }

    public void setQuantityWater(String quantityWater) {
        this.quantityWater = quantityWater;
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

    public boolean isTshirt() {
        return tshirt;
    }

    public void setTshirt(boolean tshirt) {
        this.tshirt = tshirt;
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
}
