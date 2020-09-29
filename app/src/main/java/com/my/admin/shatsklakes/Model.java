package com.my.admin.shatsklakes;

/**
 * Created by admin on 27.01.2019.
 */

public class Model {

    private String title;
    private String street;
    private String image;
    private String place;
    private String time;
    private String phoneNumber;

    public Model(String title, String street, String image, String place, String time, String phoneNumber) {
        this.title = title;
        this.street = street;
        this.image = image;
        this.place = place;
        this.time = time;
        this.phoneNumber = phoneNumber;
    }

    public Model(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}