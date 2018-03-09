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


    RadioButton OverNightYes, OverNightNo, DeeJayYes, DeeJayNo;
    EditText VenueNumber;
    Boolean OverNight = null, DeeJay = null;

    //  String
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__questions);


        VenueNumber = findViewById(R.id.btnVenueNumber);

        OverNightYes = findViewById(R.id.RadOverNightYes);
        OverNightNo = findViewById(R.id.RadOverNightNo);
        DeeJayNo = findViewById(R.id.RadDeeJayNo);
        DeeJayYes = findViewById(R.id.RadDeeJayYes);


    }


    public void NexttoThird_Host(View view) {
        if (OverNightYes.isChecked()) {

            OverNight = true;

        }
        if (OverNightYes.isChecked()) {
            OverNight = false;

        }
        if (DeeJayYes.isChecked()) {

            DeeJay = true;

        }
        if (DeeJayNo.isChecked()) {
            DeeJay = false;
        }


        String holdNumber = VenueNumber.getText().toString();

        if (!holdNumber.isEmpty()) {

            if (!OverNightYes.isChecked() || !OverNightNo.isChecked()) {

                Intent i = new Intent(Venue_Questions.this, Third_Host.class);

                i.putExtra("DeeJay", DeeJay);
                i.putExtra("OverNight", OverNight);
                i.putExtra("holdNumber", holdNumber);
                startActivity(i);

            } else {
                Toast.makeText(this, " Enter number of people ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}