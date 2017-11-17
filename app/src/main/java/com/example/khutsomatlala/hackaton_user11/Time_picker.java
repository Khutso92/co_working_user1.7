package com.example.khutsomatlala.hackaton_user11;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Time_picker extends AppCompatActivity {

    //dialog
    private static Button timeIn, timeout, btnok;
    private static TextView tvin, tvout;

    int hourOfDay;
    int minute;
    int second;


    int hourIn, hourOut;

    String name, date;
    String times = "available time bookings \n\n";

    boolean btnTimein = false, btnTimeOut = true;
    Dialog dialog;

    Button btn_availability;


    TextView dataShap;

    private DatabaseReference mPlacesReference;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCheckSpaceReference;

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

        dataShap = (TextView) findViewById(R.id.dataShap);
        btn_availability = (Button) findViewById(R.id.Availability);

        //Display the TimePicker initial time
        tvin.setText("time in \n " + hourOfDay + ":" + minute + ":" + second);
        tvout.setText("time out \n " + hourOfDay + ":" + minute + ":" + second);


        Intent i = getIntent();

        name = i.getStringExtra("placeName");
        date = i.getStringExtra("date");

        timeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeInpicker();

            }


        });

        timeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeOutpicker();
            }


        });

        mPlacesReference = FirebaseDatabase.getInstance().getReference("places");
    }

    private void timeInpicker() {

        // custom dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.time_dialog);
        dialog.setTitle("Title...");


        final TimePicker dialogButton = (TimePicker) dialog.findViewById(R.id.tp);
        // if button is clicked, close the custom dialog


        dialogButton.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                tvin.setText("time in \n " + hourOfDay + ":" + minute);

                hourIn = hourOfDay;

                Toast.makeText(Time_picker.this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();

        dialog.show();

        btnTimein = true;

    }

    private void timeOutpicker() {
        // custom dialog
        final Dialog dialogOut = new Dialog(this);
        dialogOut.setContentView(R.layout.time_dialog);
        dialogOut.setTitle("Title...");


        final TimePicker dialogButton = (TimePicker) dialogOut.findViewById(R.id.tp);


        dialogButton.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                tvout.setText("time out \n " + hourOfDay + ":" + minute);


                hourOut = hourOfDay;

                Toast.makeText(Time_picker.this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        });
        dialogOut.dismiss();

        dialogOut.show();
    }

    public void btnDis(View view) {
        if (btnTimein) {
            dialog.dismiss();
            btnTimein = false;
        } else if (btnTimeOut) {

            Intent i = new Intent(this, book_new.class);

            i.putExtra("hourIn",     Integer.toString( hourIn));
            i.putExtra("hourOut",Integer.toString( hourOut));

            startActivity(i);
            dialog.dismiss();
        }
    }

    //check availability from places node
    public void CheckAvailability(View view) {

        dataShap.setText("");

        times = "available time bookings \n\n";

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCheckSpaceReference = mFirebaseDatabase.getReference().child("working_hours").child(name).child(date);

        mCheckSpaceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChildren()) {

                    String CurrentNumber = dataSnapshot.child("1:00").child("CurrentNumber").getValue().toString();
                    String time_1 = dataSnapshot.child("1:00").child("MaxSpace").getValue().toString();

                    String CurrentNumber2 = dataSnapshot.child("2:00").child("CurrentNumber").getValue().toString();
                    String time_2 = dataSnapshot.child("2:00").child("MaxSpace").getValue().toString();

                    if (Integer.parseInt(CurrentNumber) <= Integer.parseInt(time_1)) {
                        times = times + "1:00 am \n";
                    }

                    if (Integer.parseInt(CurrentNumber2) <= Integer.parseInt(time_2)) {
                        times = times + "2:00 am \n";
                    }

                    dataShap.setText(times);

                } else {
                    Toast.makeText(Time_picker.this, "place dates not allocated", Toast.LENGTH_SHORT).show();

                    dataShap.setText("place dates not allocated");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
