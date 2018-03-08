package com.example.khutsomatlala.hackaton_user11.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;

public class Third_Host extends AppCompatActivity {
    Button btn_third_host;
    String PlaceName, infor, address, placeAdress;
    EditText placeName, information, placeAddress;
    TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_slide);

        btn_third_host = (Button) findViewById(R.id.btn_next_third);

        location = findViewById(R.id.txt_placeaddress_host);
        placeName = (EditText) findViewById(R.id.edt_placename_host);
        information = (EditText) findViewById(R.id.edt_information_host);
        placeAddress = (EditText) findViewById(R.id.edt_placeAddress_host);

        //passing info
        btn_third_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlaceName = placeName.getText().toString();
                infor = information.getText().toString();
                placeAdress = placeAddress.getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (PlaceName.trim().isEmpty()) {
                        Toast.makeText(Third_Host.this, "Enter place name", Toast.LENGTH_SHORT).show();
                    } else {
                        if (placeAdress.trim().isEmpty()) {
                            Toast.makeText(Third_Host.this, "Enter suburb name", Toast.LENGTH_SHORT).show();

                        } else {
                            if (infor.trim().isEmpty()) {
                                Toast.makeText(Third_Host.this, "Enter information about the space", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent i = new Intent(Third_Host.this, Firth_Host.class);
                                i.putExtra("name", PlaceName);
                                i.putExtra("infor", infor);
                                i.putExtra("placeAddress", placeAdress);

                                Toast.makeText(Third_Host.this, "name" + PlaceName + "\ninfor " + infor + "\n suburb name" + placeAdress, Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            }
                        }
                    }
                }


            }
        });


    }


}
