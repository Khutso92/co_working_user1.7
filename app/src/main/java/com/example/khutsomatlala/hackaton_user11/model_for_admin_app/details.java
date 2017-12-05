package com.example.khutsomatlala.hackaton_user11.model_for_admin_app;



public class details {

    //Data members
    public String Latitude;
    public String Longitude;
    public String PlaceAddress;
    public String PlaceCell;
    public String PlaceHours;
    public String PlaceInfo;
    public String PlaceName;
    public int PlacePrice;
    public String PlaceWebsite;
    public String cover_pic;



    public details() {

    }

    public details(String latitude, String longitude, String placeAddress, String placeCell, String placeHours, String placeInfo, String placeName, int placePrice, String placeWebsite, String cover_pic) {
        Latitude = latitude;
        Longitude = longitude;
        PlaceAddress = placeAddress;
        PlaceCell = placeCell;
        PlaceHours = placeHours;
        PlaceInfo = placeInfo;
        PlaceName = placeName;
        PlacePrice = placePrice;
        PlaceWebsite = placeWebsite;
        this.cover_pic = cover_pic;
    }
}
