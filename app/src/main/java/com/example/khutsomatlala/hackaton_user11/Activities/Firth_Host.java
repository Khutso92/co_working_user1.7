package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;

/**
 * Created by Admin on 1/19/2018.
 */

public class Firth_Host extends Activity implements AdapterView.OnItemSelectedListener {

    Button btn_firth_host;
    String price, in, out, hours, phone, infor, PlaceName, latlon, placeAddress;
    EditText priceperHr, timein, timeout;

    String[] TimeIn = {" 1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00"};

    String[] TimeOut = {"1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00"};

    Spinner spinnerTimeIn, spinnerTimeOut;
    int hourIn, hourOut;

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


        //Spinner

        spinnerTimeIn = findViewById(R.id.spinnerTimeIn);
        spinnerTimeOut = findViewById(R.id.spinnerTimeOut);

        ArrayAdapter<String> TimeInAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeIn);
        TimeInAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeIn.setAdapter(TimeInAdapter);

        ArrayAdapter<String> TimeOutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeOut);
        TimeOutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOut.setAdapter(TimeOutAdapter);


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
                  /*  if (in.trim().isEmpty()) {
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
                    }*/

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
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String itemIn = spinnerTimeIn.getSelectedItem().toString();

        String itemOut = spinnerTimeOut.getSelectedItem().toString();

        //Time in
        if (itemIn == TimeIn[position].toString()) {

            switch (itemIn) {
                case "1:00":
                    hourIn = 1;
                    break;
                case "2:00":
                    hourIn = 2;
                    break;
                case "3:00":
                    hourIn = 3;
                    break;
                case "4:00":
                    hourIn = 4;
                    break;
                case "5:00":
                    hourIn = 5;
                    break;
                case "6:00":
                    hourIn = 6;
                    break;
                case "7:00":
                    hourIn = 7;
                    break;
                case "8:00":
                    hourIn = 8;
                    break;
                case "9:00":
                    hourIn = 9;
                    break;
                case "10:00":
                    hourIn = 10;
                    break;
                case "11:00":
                    hourIn = 11;
                    break;
                case "12:00":
                    hourIn = 12;
                    break;
                case "13:00":
                    hourIn = 13;
                    break;
                case "14:00":
                    hourIn = 14;
                    break;
                case "15:00":
                    hourIn = 15;
                    break;
                case "16:00":
                    hourIn = 16;
                    break;
                case "17:00":
                    hourIn = 17;
                    ;
                    break;
                case "18:00":
                    hourIn = 18;
                    break;
                case "19:00":
                    hourIn = 19;
                    break;
                case "20:00":
                    hourIn = 20;
                    break;
                case "21:00":
                    hourIn = 21;
                    break;
                case "22:00":
                    hourIn = 22;
                    break;
                case "23:00":
                    hourIn = 23;

            }


        }

        //Time out
        if (itemOut == TimeOut[position].toString()) {

            switch (itemOut) {
                case "1:00 ":
                    hourOut = 1;
                    break;
                case "2:00":
                    hourOut = 2;
                    break;
                case "3:00":
                    hourOut = 3;
                    break;
                case "4:00":
                    hourOut = 4;
                    break;
                case "5:00":
                    hourOut = 5;
                    break;
                case "6:00":
                    hourOut = 6;
                    break;
                case "7:00":
                    hourOut = 7;
                    break;
                case "8:00":
                    hourOut = 8;
                    break;
                case "9:00":
                    hourOut = 9;
                    break;
                case "10:00":
                    hourOut = 10;
                    break;
                case "11:00":
                    hourOut = 11;
                    break;
                case "12:00":
                    hourOut = 12;
                    break;
                case "13:00":
                    hourOut = 13;
                    break;
                case "14:00":
                    hourOut = 14;
                    break;
                case "15:00":
                    hourOut = 15;
                    break;
                case "16:00":
                    hourOut = 16;
                    break;
                case "17:00":
                    hourOut = 17;
                    break;
                case "18:00":
                    hourOut = 18;
                    break;
                case "19:00":
                    hourOut = 19;

                    break;
                case "20:00":
                    hourOut = 20;
                    break;
                case "21:00":
                    hourOut = 21;
                    break;
                case "22:00":
                    hourOut = 22;
                    break;
                case "23:00":
                    hourOut = 23;

            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
