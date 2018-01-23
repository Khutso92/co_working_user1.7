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

public class Firth_Host extends Activity {

    Button btn_firth_host;
    String price, in, out, hours, phone, infor, PlaceName, latlon, placeAddress;
    EditText priceperHr, timein, timeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firth);

        btn_firth_host = (Button) findViewById(R.id.btn_next_firth);

        priceperHr = findViewById(R.id.edt_price_host);
        timein = findViewById(R.id.edt_timein_host);
        timeout = findViewById(R.id.edt_timeout_host);


        Intent i = getIntent();
        hours = i.getStringExtra("hour");
        phone = i.getStringExtra("phone");
        PlaceName = i.getStringExtra("name");
        infor = i.getStringExtra("infor");
        placeAddress = i.getStringExtra("placeAddress");


        //   Toast.makeText(this, "hours -"+hours +"\n"+"cell -"+ phone +"\n place name"+ PlaceName + "\n Infor"+infor +"\n place address"+ placeAddress  , Toast.LENGTH_SHORT).show();

        btn_firth_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                price = priceperHr.getText().toString();
                in = timein.getText().toString();
                out = timeout.getText().toString();

                if (price.trim().isEmpty()) {

                    Toast.makeText(Firth_Host.this, "enter price", Toast.LENGTH_SHORT).show();
                } else {
                    if (in.trim().isEmpty()) {
                        Toast.makeText(Firth_Host.this, "enter time in", Toast.LENGTH_SHORT).show();
                    } else {
                        if (out.trim().isEmpty()) {
                            Toast.makeText(Firth_Host.this, "enter time out", Toast.LENGTH_SHORT).show();
                        } else {

                            Intent i = new Intent(Firth_Host.this, Sixth_Host.class);
                            i.putExtra("price", price);
                            i.putExtra("timein", in);
                            i.putExtra("timeout", out);
                            i.putExtra("hour", hours);
                            i.putExtra("phone", phone);
                            i.putExtra("name", PlaceName);
                            i.putExtra("infor", infor);
                            i.putExtra("placeAddress", placeAddress);
                            startActivity(i);
                        }
                    }
                }


            }
        });
    }
}
