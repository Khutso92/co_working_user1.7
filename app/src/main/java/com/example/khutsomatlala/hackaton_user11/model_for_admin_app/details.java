package com.example.khutsomatlala.hackaton_user11.model_for_admin_app;



public class
details {

    //Data members
    public String Latitude;
    public String Longitude;
    public String PlaceAddress;
    public String PlaceCell;
    public String PlaceHours;
    public String PlaceInfo;
    public String PlaceName;
    public int PlacePrice;
    public String cover_pic;



    public details() {

    }

    public details(String Latitude, String Longitude, String placeAddress, String placeCell, String placeHours, String placeInfo, String placeName, int placePrice , String cover_pic) {
      this.Latitude = Latitude;
      this.Longitude = Longitude;
        PlaceAddress = placeAddress;
        PlaceCell = placeCell;
        PlaceHours = placeHours;
        PlaceInfo = placeInfo;
        PlaceName = placeName;
        PlacePrice = placePrice;

        this.cover_pic = cover_pic;
    }
}
