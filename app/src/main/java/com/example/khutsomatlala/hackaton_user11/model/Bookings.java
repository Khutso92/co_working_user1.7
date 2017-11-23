package com.example.khutsomatlala.hackaton_user11.model;



public class Bookings {

    public String names;
    public String booked_hours;
    public  String start_time;
    public  String end_time;
    public String date_booked;
    public String numberOfPeople;
    public String Price;



    public Bookings(String names, String booked_hours, String start_time, String end_time, String date_booked, String numberOfPeople, String price ) {

        this.names = names;
        this.booked_hours = booked_hours;
        this.start_time = start_time;
        this.end_time = end_time;
        this.date_booked = date_booked;
        this.numberOfPeople = numberOfPeople;
        Price = price;

    }
}
