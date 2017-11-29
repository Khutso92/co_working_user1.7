package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.Profile;
import com.example.khutsomatlala.ProfilePojo;
import com.example.khutsomatlala.hackaton_user11.model.Bookings;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 9/15/2017.
 */

public class book_new extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    //Profile adapter
    DatabaseReference db;
    Boolean saved;
    ArrayList<ProfilePojo> profileList = new ArrayList<>();
    private FirebaseAuth mAuth;

    int hourIn, hourOut, personNumber, totalPrice;

    TextView mPrice,   txtPrice,txtTimein,txtTimeOut,txtDateBooked,txtNumberOfppl, nameOfPerson;

    String dayStamp = new SimpleDateFormat("yyyy - MM - dd").format(new Date());
    Spinner spinnerTimeIn, spinnerTimeOut;
    Button book, add, subtract;


    Date  Cal_date;
    private DatabaseReference mCheckSpaceReference, mbookingReference;

    String details = "Booking Details\n\n",Time_in,Time_out,numberofPeople;

    DateFormat formatDateTime = DateFormat.getDateTimeInstance();

    Calendar dateTime = Calendar.getInstance();


    private FirebaseDatabase mFirebaseDatabase;

    int hourOfDay, minute, second, minteger = 0, bookBlocker = 0;
    String in_hour, out_hour, month, year, day, date, user_uid, open_time, close_time, pic, name, pricee, mEmail, mUsername,placeName;

    //Calendar
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());


    String[] TimeIn = {" 1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00"};

    String[] TimeOut = {"1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00"};


    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //  setContentView(R.layout.activity_book);
        setContentView(R.layout.activity_book);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        Intent i = getIntent();

        pic = i.getStringExtra("pic");
        name = i.getStringExtra("name");
        pricee = i.getStringExtra("price");
        mEmail = i.getStringExtra("email");
        mUsername = i.getStringExtra("mUsername");
        user_uid = i.getStringExtra("user_uid");
        in_hour = i.getStringExtra("hourIn");
        out_hour = i.getStringExtra("hourOut");



        mPrice = (TextView) findViewById(R.id.txtPrice);

        txtPrice = (TextView) findViewById(R.id.txtPrice);


        //Get a new instance of Calendar
        final Calendar c = Calendar.getInstance();
        hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
        minute = c.get(c.MINUTE); //Current Minute
        second = c.get(c.SECOND); //Current Second

        book = (Button) findViewById(R.id.btn_book);
        add = (Button) findViewById(R.id.btn_pos);
        subtract = (Button) findViewById(R.id.btn_neg);

        txtTimein = (TextView) findViewById(R.id.txtTimein);
        txtTimeOut = (TextView) findViewById(R.id.txtTimeout);
        txtDateBooked = (TextView) findViewById(R.id.txtDateBooked);
        txtNumberOfppl = (TextView) findViewById(R.id.txtNumberPpl);
        nameOfPerson = findViewById(R.id.nameOfPerson);

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

        //Calendar
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(" MMMM yyyy");


        final ActionBar actionBar = getSupportActionBar();

       actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(  dateFormat.format(cal.getTime() ));

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                year = "20" + (dateClicked.getYear() - 100);

                day = "" + dateClicked.getDate();

                month = "" + (dateClicked.getMonth() + 1);

                date = day + "-" + month + "-" + year;

                Toast.makeText(book_new.this, "" + date, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionBar.setTitle(dateFormat.format(firstDayOfNewMonth));


                Cal_date = firstDayOfNewMonth;
            }


        });


    }


    public void book(View view) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCheckSpaceReference = mFirebaseDatabase.getReference();

        mCheckSpaceReference.child("working_hours").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChildren()) {

                    open_time = dataSnapshot.child("open_time").getValue().toString();
                    close_time = dataSnapshot.child("open_time").getValue().toString();

                    if (hourIn >= Integer.parseInt(open_time)) {
                        //  Toast.makeText(book_new.this, "Valid date for in time", Toast.LENGTH_SHORT).show();

                        if (hourOut >= Integer.parseInt(open_time)) {

                            //Toast.makeText(book_new.this, "Valid date close time", Toast.LENGTH_SHORT).show();

                            //TODO  the booking here


                            if (hourOut > hourIn) {


                                //Making a booking
                                mbookingReference = mFirebaseDatabase.getReference().child("booking").child("user_id").child(user_uid);
                                mbookingReference.child(name).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Bookings bookings = new Bookings(mUsername+"",""+name,Integer.toString(hourOut - hourIn), Integer.toString(hourIn), Integer.toString(hourOut), date, Integer.toString(personNumber), Integer.toString(totalPrice));

                                        String key = mbookingReference.push().getKey();


                                        Time_in = Integer.toString( hourIn);
                                        Time_out = Integer.toString( hourOut);

                                        if (bookBlocker == 0) {
                                            mbookingReference.child(key).setValue(bookings);
                                            Toast.makeText(book_new.this, "Successfully booked", Toast.LENGTH_SHORT).show();
                                            bookBlocker++;
                                        } else {
                                            Toast.makeText(book_new.this, "You have already booked", Toast.LENGTH_SHORT).show();
                                        }

                                    }


                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(book_new.this, "Time in must be lesser than time out", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(book_new.this, name +" closed at " + close_time+":00", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(book_new.this, name +" opens at - "+ open_time + ":00" , Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(book_new.this, "No values in the DB", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("user_uid", user_uid);
        intent.putExtra("mUsername", mUsername);

        startActivity(intent);


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
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void increase(View view) {

        minteger = minteger + 1;
        display(minteger);

    }

    public void decrease(View view) {

        if (minteger > 0) {
            minteger = minteger - 1;
            display(minteger);


        } else {
            Toast.makeText(this, "cant be less than zero", Toast.LENGTH_SHORT).show();
        }
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(R.id.tvNoOfPpl);
        displayInteger.setText("" + number);

        personNumber = number;
        numberofPeople = Integer.toString(personNumber);

        try {
            //txtTotalPrice.setText("R"+number * Integer.parseInt( ""+price));

            totalPrice = Integer.parseInt("" + pricee) * number;

          //  Toast.makeText(this, "R" + totalPrice, Toast.LENGTH_SHORT).show();
            nameOfPerson.setText("Name " + mUsername);
            txtTimein.setText("Time in - " + hourIn +":00");
            txtTimeOut.setText("Time out - " + hourOut +":00");
            txtDateBooked.setText("Date booked - "+ date );
            txtNumberOfppl.setText("Number of people - "+  numberofPeople );

            txtPrice.setText("R " + totalPrice);
        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
        }

    }


    //Log out menu
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();


        //respond to menu item selection

        switch (item.getItemId()) {
            case R.id.about:
                mAuth.signOut();

                Intent i = new Intent(book_new.this, Auth_login.class);
                startActivity(i);

        }

        switch (item.getItemId()) {
            case R.id.profile:


                Intent intent = new Intent(this, Profile.class);
                intent.putExtra("user_uid", user_uid);
                intent.putExtra("mUsername", mUsername);
                Toast.makeText(this, "" + mUsername, Toast.LENGTH_SHORT).show();

                startActivity(intent);


        }
        return true;

    }


}

