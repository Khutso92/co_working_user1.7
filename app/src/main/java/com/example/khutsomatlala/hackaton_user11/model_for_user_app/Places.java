package com.example.khutsomatlala.hackaton_user11.model_for_user_app;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khutsomatlala on 2017/08/29.
 */

public class Places implements Serializable {

    //Data members
    public String placeName;
    public String placeInfo;
    public String placeAddress;
    public String placeCell;
    public String placeHours;
    public String placeWebsite;
    public String placeLongitude;
    public String placeLatitude;
    private String urI;
    private String email;

    private List<Feature> Features;


    //Like
    private String like;
    private int num;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }


    //For user

    public Places() {

    }


    public Places(String comment, String rate) {

    }

    public Places(String placeName, String placeInfo, String placeAddress, String placeCell, String placeHours, String placeWebsite, String placeLongitude, String placeLatitude, String urI, String email, List<Feature> features, String like, int num) {
        this.placeName = placeName;
        this.placeInfo = placeInfo;
        this.placeAddress = placeAddress;
        this.placeCell = placeCell;
        this.placeHours = placeHours;
        this.placeWebsite = placeWebsite;
        this.placeLongitude = placeLongitude;
        this.placeLatitude = placeLatitude;
        this.urI = urI;
        this.email = email;
        Features = features;
        this.like = like;
        this.num = num;
    }

    //getters
    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceInfo() {
        return placeInfo;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public String getPlaceCell() {
        return placeCell;
    }

    public String getPlaceHours() {
        return placeHours;
    }


    public String getPlaceWebsite() {
        return placeWebsite;
    }

    public String getPlaceLongitude() {
        return placeLongitude;
    }

    public String getPlaceLatitude() {
        return placeLatitude;
    }

    public String getUrI() {
        return urI;
    }

    public String getEmail() {
        return email;
    }


    public List<Feature> getFeatures() {
        return Features;
    }

    public void setFeatures(List<Feature> features) {
        Features = features;
    }
}
