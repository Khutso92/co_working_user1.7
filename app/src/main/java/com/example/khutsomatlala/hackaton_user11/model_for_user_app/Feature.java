package com.example.khutsomatlala.hackaton_user11.model_for_user_app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;



public class Feature {

    String title, imageUrl;

    public Feature(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public Feature() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tietle", title);
        result.put("imageUrl", imageUrl);
        return result;
    }

}
