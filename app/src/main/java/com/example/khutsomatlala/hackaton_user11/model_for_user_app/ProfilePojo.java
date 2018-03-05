package com.example.khutsomatlala.hackaton_user11.model_for_user_app;

/**
 * Created by Admin on 11/27/2017.
 */

public class ProfilePojo {


     String placeName;
    String start_time;
    String end_time;
    String date_booked;
    String numberOfPeople;
 private String Price;
    String image;
    String place_image;


    public ProfilePojo() {
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getDate_booked() {
        return date_booked;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return image;
    }

    public String getPlace_image() {
        return place_image;
    }
}
