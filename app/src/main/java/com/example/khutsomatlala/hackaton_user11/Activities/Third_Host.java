package com.example.khutsomatlala.hackaton_user11.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;

/**
 * Created by Admin on 1/19/2018.
 */

public class Third_Host extends AppCompatActivity {
    Button btn_third_host;
    String PlaceName, infor, address, placeAdress;
    EditText placeName, information, placeAddress;
    TextView location;


    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_slide);

        btn_third_host = (Button) findViewById(R.id.btn_next_third);

        location = findViewById(R.id.txt_placeaddress_host);
        placeName = (EditText) findViewById(R.id.edt_placename_host);
        information = (EditText) findViewById(R.id.edt_information_host);
        placeAddress = (EditText) findViewById(R.id.edt_placeAddress_host);




      /*  placeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder =  new PlacePicker.IntentBuilder();

                Intent intent;
                try {

                    intent = builder.build((Activity) getApplicationContext());
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });*/

        //passing info
        btn_third_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlaceName = placeName.getText().toString();
                infor = information.getText().toString();
                placeAdress = placeAddress.getText().toString();


                if (PlaceName.trim().isEmpty()) {
                    Toast.makeText(Third_Host.this, "enter place name", Toast.LENGTH_SHORT).show();
                } else {
                    if (placeAdress.trim().isEmpty()) {
                        Toast.makeText(Third_Host.this, "Enter address", Toast.LENGTH_SHORT).show();

                    } else {
                        if (infor.trim().isEmpty()) {
                       Toast.makeText(Third_Host.this, "enter infor", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent i = new Intent(Third_Host.this, Fourth_Host.class);
                            i.putExtra("name", PlaceName);
                            i.putExtra("infor", infor);
                            i.putExtra("placeAddress", placeAdress);
                            startActivity(i);
                        }
                    }
                }


            }
        });


    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode==PLACE_PICKER_REQUEST){
            if (requestCode ==RESULT_OK){
                Place place =  PlacePicker.getPlace(data,this);
           address = String.format("Places: %s",place.getLatLng());

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/


}
