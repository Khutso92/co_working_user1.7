package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.model.Bookings;
import com.example.khutsomatlala.hackaton_user11.model.CurrentNumber;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class Time_picker extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static Button timeIn, timeout, btnok;
    private static TextView tvin, tvout;

    int minteger = 0;

    String[] TimeIn = {"time in ", "1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00", "00:00"};


    String[] TimeOut = {"time out ", "1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00", "00:00"};

    int hourIn, hourOut, personNumber, totalPrice;

    //Number of user in space
    String CurrentNumber_11;

    //Time for a space
    String time_11;

    int i = 0;
    String name, date, price;
    String times = "available time bookings \n\n";
    TextView dataShap, txtTotalPrice;

    private DatabaseReference mbookingReference;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCheckSpaceReference;

    Spinner spinnerTimeIn, spinnerTimeOut;
    Button book, add, subtract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);


        //Get the widgets reference from XML layout
        tvin = (TextView) findViewById(R.id.tvIn);
        tvout = (TextView) findViewById(R.id.tvOut);
        TimePicker tp = (TimePicker) findViewById(R.id.tp);
        timeIn = (Button) findViewById(R.id.time_in);
        timeout = (Button) findViewById(R.id.time_out);
        btnok = (Button) findViewById(R.id.btnOk);

        book = (Button) findViewById(R.id.btn_book);
        add = (Button) findViewById(R.id.btn_pos);
        subtract = (Button) findViewById(R.id.btn_neg);


        spinnerTimeIn = (Spinner) findViewById(R.id.spinnerTimeIn);
        spinnerTimeOut = (Spinner) findViewById(R.id.spinnerTimeOut);

        spinnerTimeOut.setOnItemSelectedListener(this);
        spinnerTimeIn.setOnItemSelectedListener(this);

        ArrayAdapter<String> TimeInAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeIn);
        TimeInAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeIn.setAdapter(TimeInAdapter);

        ArrayAdapter<String> TimeOutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeOut);
        TimeOutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOut.setAdapter(TimeOutAdapter);

        dataShap = (TextView) findViewById(R.id.dataShap);

        Intent i = getIntent();
        name = i.getStringExtra("placeName");
        date = i.getStringExtra("date");
        price = i.getStringExtra("price");

        spinnerTimeIn.setEnabled(false);
        spinnerTimeOut.setEnabled(false);
        book.setEnabled(false);
        add.setEnabled(false);
        subtract.setEnabled(false);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }


    //check availability from places node
    public void CheckAvailability(View view) {

        dataShap.setText("");
        times = "available time bookings \n\n";

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        try {
            mCheckSpaceReference = mFirebaseDatabase.getReference().child("working_hours").child(name).child(date);

            mCheckSpaceReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.hasChildren()) {

                        //11:00 am
                        CurrentNumber_11 = dataSnapshot.child("11:00").child("CurrentNumber").getValue().toString();
                        time_11 = dataSnapshot.child("11:00").child("MaxSpace").getValue().toString();


                        if (Integer.parseInt(CurrentNumber_11) <= Integer.parseInt(time_11)) {
                            times = times + "11:00   \n";

                        }

                        dataShap.setText(times + "\n" + dataSnapshot.getChildrenCount());

                        // if time is available for booking
                        spinnerTimeIn.setEnabled(true);
                        spinnerTimeOut.setEnabled(true);
                        add.setEnabled(true);
                        subtract.setEnabled(true);


                    } else {

                        dataShap.setText("Dates not yet allocated, please select another space ");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong with the database", Toast.LENGTH_SHORT).show();

            dataShap.setText("Please restart the application ");
        }
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
                    break;
                case "00:00":
                    hourIn = 12;
                    break;
            }

            dataShap.setText(hourIn + "");

        }

        //Time out
        if (itemOut == TimeOut[position].toString()) {

            switch (itemOut) {
                case "1:00":
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
                    break;
                case "00:00":
                    hourOut = 12;
                    break;
            }


            book.setEnabled(true);
            dataShap.setText(hourOut + "");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void validateTime(View view) {

        if ((hourIn < hourOut)) {

            mFirebaseDatabase = FirebaseDatabase.getInstance();

            try {
                mCheckSpaceReference = mFirebaseDatabase.getReference().child("working_hours").child(name).child(date);

                mCheckSpaceReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (dataSnapshot.hasChildren()) {

                            //11:00 am time in
                            String CurrentNumber_11 = dataSnapshot.child("11:00").child("CurrentNumber").getValue().toString();
                            String MaxSpace11 = dataSnapshot.child("11:00").child("MaxSpace").getValue().toString();
                            String time_11 = dataSnapshot.child("11:00").child("time").getValue().toString();


                            //Checking if the Hour in exist in the DB
                            if (Integer.toString(hourIn) == time_11) {

                                if (Integer.parseInt(CurrentNumber_11) <= Integer.parseInt(MaxSpace11)) {

                                    if (i == 0) {
                                        i++;
                                        CurrentNumber user = new CurrentNumber(Integer.parseInt(CurrentNumber_11) + 1);
                                        Map<String, Object> postValues = user.toMap();

                                        mCheckSpaceReference.child("11:00").updateChildren(postValues);
                                        // mCheckSpaceReference.child("12:00").removeValue();

                                    }

                                    //Making a booking
                                    mbookingReference = mFirebaseDatabase.getReference().child("bookings").child(name);
                                    mbookingReference.child(name).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Bookings bookings = new Bookings("Kwaja", Integer.toString(hourOut - hourIn), Integer.toString(hourIn), Integer.toString(hourOut), date, Integer.toString(personNumber), Integer.toString(totalPrice));

                                            String key = mbookingReference.push().getKey();

                                            mbookingReference.child(key).setValue(bookings);

                                            Toast.makeText(Time_picker.this, "Successfully booked", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                } else {

                                    dataShap.setText("Fully booked ");

                                }
                            }


                        } else {

                            Toast.makeText(Time_picker.this, "No values in the DB  ", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            } catch (Exception e) {

                Toast.makeText(this, "Something went wrong with the database", Toast.LENGTH_SHORT).show();

                dataShap.setText("Please restart the application ");
            }

        } else {

            dataShap.setText("invalid time,Time in must be lesser than time out");
        }

    }

    public void increase(View view) {

        minteger = minteger + 1;
        display(minteger);

    }

    public void decrease(View view) {

        if (minteger > 0) {
            minteger = minteger - 1;
            display(minteger);
        }
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(R.id.tvNoOfPpl);
        displayInteger.setText("" + number);

        personNumber = number;

        try {
            //txtTotalPrice.setText("R"+number * Integer.parseInt( ""+price));

            totalPrice = Integer.parseInt("" + price) * number;

            Toast.makeText(this, "R" + totalPrice, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
        }

    }


}
