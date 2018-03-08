package com.example.khutsomatlala.hackaton_user11.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.Property_Questions;
import com.example.khutsomatlala.hackaton_user11.R;

public class Activity_Host extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton rbcws, rbvenue, rbproperty;
    Button btn_radio;
    String host_type;

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
                        Intent i = new Intent(Activity_Host.this, Third_Host.class);
                        host_type = "new_places";
                        Toast.makeText(Activity_Host.this, "" + host_type, Toast.LENGTH_SHORT).show();

                     startActivity(i);
                        break;

                    case R.id.venue:
                        Intent intent = new Intent(Activity_Host.this, Venue_Questions.class);

                        host_type = "events";
                        Toast.makeText(Activity_Host.this, "" + host_type, Toast.LENGTH_SHORT).show();
                      startActivity(intent);

                        break;

                    case R.id.property:
                        Intent intentp = new Intent(Activity_Host.this, Property_Questions.class);
                        host_type = "property";
                        Toast.makeText(Activity_Host.this, "" + host_type, Toast.LENGTH_SHORT).show();
                        //startActivity(intentp);
                }
            }
        });

        ;
    }
}
