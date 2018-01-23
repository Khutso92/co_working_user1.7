package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;

/**
 * Created by Admin on 1/19/2018.
 */

public class Fourth_Host extends Activity {

    Button btn_fourth_host;
    String hours, phone, infor, PlaceName, latlon, placeAddress;
    EditText working_hours, telephone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_slide);

        btn_fourth_host = (Button) findViewById(R.id.btn_next_forth);

        working_hours = (EditText) findViewById(R.id.edt_hours_host);
        telephone = findViewById(R.id.edt_phone_host);


        Intent i = getIntent();
        PlaceName = i.getStringExtra("name");
        placeAddress = i.getStringExtra("placeAddress");
        infor = i.getStringExtra("infor");

        //  Toast.makeText(this, "place name - "+"\n" +PlaceName+ "Suburb -"+placeAddress+" \n infor - "+ infor , Toast.LENGTH_SHORT).show();


        btn_fourth_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hours = working_hours.getText().toString();
                phone = telephone.getText().toString();

                if (phone.trim().isEmpty()) {
                    Toast.makeText(Fourth_Host.this, "enter telephone", Toast.LENGTH_SHORT).show();
                } else {
                    if (hours.trim().isEmpty()) {

                        Toast.makeText(Fourth_Host.this, "enter hours", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(Fourth_Host.this, Firth_Host.class);
                        i.putExtra("phone", phone);
                        i.putExtra("hour", hours);
                        i.putExtra("name", PlaceName);
                        i.putExtra("infor", infor);
                        i.putExtra("placeAddress", placeAddress);
                        startActivity(i);
                    }
                }



            }
        });
    }
}
