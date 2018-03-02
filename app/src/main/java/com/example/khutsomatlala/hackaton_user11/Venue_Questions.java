package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.khutsomatlala.hackaton_user11.Activities.Third_Host;

public class Venue_Questions extends AppCompatActivity {

    Button btn_next_venue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__questions);

        btn_next_venue = findViewById(R.id.btn_next_venue);

        btn_next_venue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Venue_Questions.this, Third_Host.class);
                startActivity(i);
            }
        });
    }
}
