package com.example.khutsomatlala.hackaton_user11.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class CurrentNumber {

    int CurrentNumber;



    public CurrentNumber() {


    }

    public CurrentNumber(int CurrentNumber) {
        this.CurrentNumber = CurrentNumber;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("CurrentNumber", CurrentNumber);

      return    result;
    }
}
