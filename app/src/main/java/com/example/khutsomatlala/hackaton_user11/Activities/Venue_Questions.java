package com.example.khutsomatlala.hackaton_user11.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;

public class Venue_Questions extends AppCompatActivity {

    Button next_venue;
    RadioButton OverNightYes, OverNightNo, DeeJayYes, DeeJayNo;
EditText VenueNumber;
    Boolean OverNight, DeeJay;

    //  String
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__questions);

        next_venue = findViewById(R.id.btn_next_venue);
        VenueNumber = findViewById(R.id.btnVenueNumber);

        OverNightYes = findViewById(R.id.RadOverNightYes);
        OverNightNo = findViewById(R.id.RadOverNightNo);
        DeeJayNo = findViewById(R.id.RadDeeJayNo);
        DeeJayYes = findViewById(R.id.RadDeeJayYes);

        next_venue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Venue_Questions.this, Third_Host.class);
                startActivity(i);
            }
        });


    }


    public void y(View view) {
        if (OverNightYes.isChecked()) {

            OverNight = true;
            Toast.makeText(this, "over night " + OverNight, Toast.LENGTH_SHORT).show();
        } else {
            OverNight = false;
            Toast.makeText(this, "over night  " + OverNight, Toast.LENGTH_SHORT).show();

        }
        if (DeeJayYes.isChecked()) {

            DeeJay = true;
            Toast.makeText(this, "Dee jay " + DeeJay, Toast.LENGTH_SHORT).show();
        } else {
            DeeJay = false;
            Toast.makeText(this, "Dee jay  " + DeeJay, Toast.LENGTH_SHORT).show();
        }


        String holdNumber = VenueNumber.getText().toString();

        Toast.makeText(this, ""+holdNumber, Toast.LENGTH_SHORT).show();
    }
}