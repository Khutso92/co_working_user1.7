package com.example.khutsomatlala.hackaton_user11.model_for_admin_app;

/**
 * Created by Admin on 2/23/2018.
 */

public class Property {

    private String propertyType;
    private String suburb;
    private String city;
    private String propertyPrice;
    private String pic1;
    private String pic2;
    private String pic3;
    private String cell;
    private String timeIn;
    private String timeOut;
    private String  desc;
    private String  name;



    public Property(String propertyType, String suburb, String city, String propertyPrice, String pic1, String pic2, String pic3, String cell, String timeIn, String timeOut, String desc, String name) {
        this.propertyType = propertyType;
        this.suburb = suburb;
        this.city = city;
        this.propertyPrice = propertyPrice;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.cell = cell;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.desc = desc;
        this.name = name;

    }

    public String getName() {
        return name;
    }
    public String getPropertyType() {
        return propertyType;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public String getPic1() {
        return pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public String getCell() {
        return cell;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getDesc() {
        return desc;
    }
}
