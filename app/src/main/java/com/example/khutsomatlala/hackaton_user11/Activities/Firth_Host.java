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
    String price, in, out, hours, phone, infor, PlaceName, latlon, placeAddress, opentime, closetime, workinghours;
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



        spinnerTimeIn = findViewById(R.id.spinnerTimeInHost);
        spinnerTimeOut = findViewById(R.id.spinnerTimeOutHost);

        ArrayAdapter<String> TimeInAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeIn);
        TimeInAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeIn.setAdapter(TimeInAdapter);

        ArrayAdapter<String> TimeOutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeOut);
        TimeOutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOut.setAdapter(TimeOutAdapter);

        spinnerTimeOut.setOnItemSelectedListener(this);
        spinnerTimeIn.setOnItemSelectedListener(this);

        //   Toast.makeText(this, "hours -"+hours +"\n"+"cell -"+ phone +"\n place name"+ PlaceName + "\n Infor"+infor +"\n place address"+ placeAddress  , Toast.LENGTH_SHORT).show();

  /*      btn_firth_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                price = priceperHr.getText().toString();
                in = timein.getText().toString();
                out = timeout.getText().toString();

                if (price.trim().isEmpty()) {

                    Toast.makeText(Firth_Host.this, "enter price", Toast.LENGTH_SHORT).show();

                    workinghours = opentime +"-"+closetime;
                    Toast.makeText(Firth_Host.this, ""+workinghours, Toast.LENGTH_SHORT).show();
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
*/
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
                    opentime = hourIn + "AM";

                    break;
                case "2:00":
                    hourIn = 2;
                    opentime = hourIn + "AM";
                    break;
                case "3:00":
                    hourIn = 3;
                    opentime = hourIn + "AM";

                    break;
                case "4:00":
                    hourIn = 4;
                    opentime = hourIn + "AM";
                    break;
                case "5:00":
                    hourIn = 5;
                    opentime = hourIn + "AM";
                    break;
                case "6:00":
                    hourIn = 6;
                    opentime = hourIn + "AM";
                    break;
                case "7:00":
                    hourIn = 7;
                    opentime = hourIn + "AM";
                    break;
                case "8:00":
                    hourIn = 8;
                    opentime = hourIn + "AM";
                    break;
                case "9:00":
                    hourIn = 9;
                    opentime = hourIn + "AM";
                    break;
                case "10:00":
                    hourIn = 10;
                    opentime = hourIn + "AM";
                    break;
                case "11:00":
                    hourIn = 11;
                    opentime = hourIn + "AM";
                    break;
                case "12:00":
                    hourIn = 12;
                    opentime = "12PM";
                    break;
                case "13:00":
                    hourIn = 13;
                    opentime = "1PM";
                    break;
                case "14:00":
                    hourIn = 14;
                    opentime = "2PM";
                    break;
                case "15:00":
                    hourIn = 15;
                    opentime = "3PM";

                    break;
                case "16:00":
                    hourIn = 16;
                    opentime = "4PM";
                    break;
                case "17:00":
                    hourIn = 17;
                    opentime = "5PM";

                    break;
                case "18:00":
                    hourIn = 18;
                    opentime = "6PM";
                    break;
                case "19:00":
                    hourIn = 19;
                    opentime = "7PM";
                    break;
                case "20:00":
                    hourIn = 20;
                    opentime = "8PM";
                    break;
                case "21:00":
                    hourIn = 21;
                    opentime = "9PM";
                    break;
                case "22:00":
                    hourIn = 22;
                    opentime = "10PM";
                    break;
                case "23:00":
                    hourIn = 23;
                    opentime = "11PM";

            }

            //Toast.makeText(this, ""+opentime, Toast.LENGTH_SHORT).show();


        }

        //Time out
        if (itemOut == TimeOut[position].toString()) {

            switch (itemOut) {
                case "1:00 ":
                    hourOut = 1;
                    closetime = "1AM";
                    break;
                case "2:00":
                    hourOut = 2;
                    closetime = "2AM";
                    break;
                case "3:00":
                    hourOut = 3;
                    closetime = "3AM";
                    break;
                case "4:00":
                    hourOut = 4;
                    closetime = "4AM";
                    break;
                case "5:00":
                    hourOut = 5;
                    closetime = "5AM";
                    break;
                case "6:00":
                    hourOut = 6;
                    closetime = "6AM";
                    break;
                case "7:00":
                    hourOut = 7;
                    closetime = "7AM";
                    break;
                case "8:00":
                    hourOut = 8;
                    closetime = "8AM";
                    break;
                case "9:00":
                    hourOut = 9;
                    closetime = "9AM";
                    break;
                case "10:00":
                    hourOut = 10;
                    closetime = "10AM";
                    break;
                case "11:00":
                    hourOut = 11;
                    closetime = "11AM";
                    break;
                case "12:00":
                    hourOut = 12;
                    closetime = "12PM";
                    break;
                case "13:00":
                    hourOut = 13;
                    closetime = "1PM";
                    break;
                case "14:00":
                    hourOut = 14;
                    closetime = "2PM";
                    break;
                case "15:00":
                    hourOut = 15;
                    closetime = "3PM";
                    break;
                case "16:00":
                    hourOut = 16;
                    closetime = "4PM";
                    break;
                case "17:00":
                    hourOut = 17;
                    closetime = "5PM";
                    break;
                case "18:00":
                    hourOut = 18;
                    closetime = "6PM";
                    break;
                case "19:00":
                    hourOut = 19;
                    closetime = "7PM";
                    break;
                case "20:00":
                    hourOut = 20;
                    closetime = "8PM";
                    break;
                case "21:00":
                    hourOut = 21;
                    closetime = "9PM";
                    break;
                case "22:00":
                    hourOut = 22;
                    closetime = "10PM";
                    break;
                case "23:00":
                    hourOut = 23;
                    closetime = "11PM";
                    workinghours = opentime + "-" + closetime;


            }

            Toast.makeText(this, ""+closetime, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "nothing selected", Toast.LENGTH_SHORT).show();

    }

    public void nextaa(View view) {
        price = priceperHr.getText().toString();
        in = timein.getText().toString();
        out = timeout.getText().toString();
        if (price.trim().isEmpty()) {

            Toast.makeText(Firth_Host.this, "enter price", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+opentime +" - "+closetime, Toast.LENGTH_SHORT).show();

        }
    }


}
