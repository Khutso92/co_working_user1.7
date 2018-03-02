package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.khutsomatlala.hackaton_user11.Activities.Second_Host;

public class Activity_Host extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton rbcws, rbvenue, rbproperty;
    Button btn_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__host);

        radioGroup = findViewById(R.id.radioGroup);
        rbcws = findViewById(R.id.cws);
        rbvenue = findViewById(R.id.venue);
        rbproperty = findViewById(R.id.property);
        btn_radio = findViewById(R.id.btn_radio);


        btn_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.cws:
                        Intent i = new Intent(Activity_Host.this, Second_Host.class);
                        startActivity(i);

                        break;

                    case R.id.venue:
                        Intent intent = new Intent(Activity_Host.this, Venue_Questions.class);
                        startActivity(intent);

                        break;

                    case R.id.property:
                        Intent intentp = new Intent(Activity_Host.this, Property_Questions.class);
                        startActivity(intentp);
                }
            }
        });

    }
}
