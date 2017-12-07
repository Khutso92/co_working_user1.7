package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    private Button BnStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }


    public void loadSlides(View view) {

        new Preference_Manager(this).clearPreferences();
        startActivity(new Intent(this, Welcome_activity.class));
        finish();
    }
}
