package com.example.khutsomatlala.hackaton_user11.model;

/**
 * Created by Admin on 11/27/2017.
 */

public class ProfilePojo {

    String names;
    String placeName;
    String start_time;
    String end_time;
    String date_booked;
    String booked_hours;
    String numberOfPeople;
    String Price;
    String Image;

    String place_image;

    public String getImage() {
        return Image;
    }

    public String getPlace_image() {
        return place_image;
    }

    public void setPlace_image(String place_image) {
        this.place_image = place_image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public ProfilePojo() {
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate_booked() {
        return date_booked;
    }

    public void setDate(String date) {
        this.date_booked = date;
    }

    public String getBooked_hours() {
        return booked_hours;
    }

    public void setBooked_hours(String booked_hours) {
        this.booked_hours = booked_hours;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
