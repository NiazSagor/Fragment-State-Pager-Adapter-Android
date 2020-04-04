package com.angik.collectiondemofragment;

public class RestaurantDetailClass {

    private String resName;
    private String resAddress;
    private String resImageUrl;
    private double rating;

    public RestaurantDetailClass() {

    }

    public RestaurantDetailClass(String resAddress, String resImageUrl, String resName, double rating) {
        this.resName = resName;
        this.resAddress = resAddress;
        this.resImageUrl = resImageUrl;
        this.rating = rating;
    }

    public String getResImageUrl() {
        return resImageUrl;
    }

    public void setResImageUrl(String resImageUrl) {
        this.resImageUrl = resImageUrl;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResAddress() {
        return resAddress;
    }

    public void setResAddress(String resAddress) {
        this.resAddress = resAddress;
    }

    public double getResRate() {
        return rating;
    }

    public void setResRate(double rating) {
        this.rating = rating;
    }
}
