package com.example.khutsomatlala.hackaton_user11.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.Bookings;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class bookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private FirebaseAuth mAuth;

    Boolean date_selected = false;
    Boolean hours_selected = false;

    int hourIn, hourOut, personNumber, totalPrice,vadilateHourIn ,vadilateHourOut;

    TextView mPrice, txtPrice, txtTimein, txtTimeOut, txtDateBooked, txtNumberOfppl, nameOfPerson,tv_month;

    String dayStamp = new SimpleDateFormat("dd").format(new Date());
    Spinner spinnerTimeIn, spinnerTimeOut;
    Button book, add, subtract;


    Date Cal_date;
    private DatabaseReference mCheckSpaceReference, mbookingReference;

    String Time_in, Time_out, numberofPeople;


    private FirebaseDatabase mFirebaseDatabase;

    int hourOfDay, minute, second, minteger = 0, bookBlocker = 0;
    String in_hour, out_hour, month, year, day, date, user_uid, open_time, close_time, pic, name, pricee, mEmail, mUsername, placeName;

    //Calendar
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM ", Locale.getDefault());


    String[] TimeIn = {" 1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00"};

    String[] TimeOut = {"1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00"};

    Calendar cal;

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

        book = findViewById(R.id.btn_book);
        add = findViewById(R.id.btn_pos);
        subtract = findViewById(R.id.btn_neg);
        tv_month = findViewById(R.id.tv_month);

        txtTimein = findViewById(R.id.txtTimein);
        txtTimeOut = findViewById(R.id.txtTimeout);
        txtDateBooked = findViewById(R.id.txtDateBooked);
        txtNumberOfppl = findViewById(R.id.txtNumberPpl);
        nameOfPerson = findViewById(R.id.nameOfPerson);

        spinnerTimeIn = findViewById(R.id.spinnerTimeIn);
        spinnerTimeOut = findViewById(R.id.spinnerTimeOut);

        spinnerTimeOut.setOnItemSelectedListener(this);
        spinnerTimeIn.setOnItemSelectedListener(this);

        ArrayAdapter<String> TimeInAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeIn);
        TimeInAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeIn.setAdapter(TimeInAdapter);

        ArrayAdapter<String> TimeOutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimeOut);
        TimeOutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOut.setAdapter(TimeOutAdapter);

        //Calendar
        cal = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");

        final ActionBar actionBar = getSupportActionBar();
        //  actionBar.setDisplayHomeAsUpEnabled(false);
        //  actionBar.setTitle(dateFormat.format(cal.getTime()));

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        tv_month.setText(  new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                year = "20" + (dateClicked.getYear() - 100);
                day = "0" + dateClicked.getDate();
                month = "" + (dateClicked.getMonth() + 1);

                if (date_selected == true) {

                    txtDateBooked.setText("Date booked - " + date);
                } else {
                    txtDateBooked.setText("Date booked - date not selected");
                }

                Date todaysDate = new Date();

                if (todaysDate.before(dateClicked) || day.equals(dayStamp.toString())) {
                    //  Toast.makeText(bookingActivity.this, "valid  date ", Toast.LENGTH_SHORT).show();
                    date = day + "-" + month + "-" + year;

                    date_selected = true;

                    txtDateBooked.setText("Date booked - " + date);
                } else {
                    Toast.makeText(bookingActivity.this, "date has pasted " + dayStamp, Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                // actionBar.setTitle(dateFormat.format(firstDayOfNewMonth));
                Cal_date = firstDayOfNewMonth;
            }
        });


        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCheckSpaceReference = mFirebaseDatabase.getReference();

        mCheckSpaceReference.child("new_working_hours").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vadilateHourIn =  Integer.parseInt( dataSnapshot.child("open_time").getValue().toString());
                vadilateHourOut =  Integer.parseInt( dataSnapshot.child("close_time").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void book(View view) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCheckSpaceReference = mFirebaseDatabase.getReference();

        mCheckSpaceReference.child("new_working_hours").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    open_time = dataSnapshot.child("open_time").getValue().toString();
                    close_time = dataSnapshot.child("close_time").getValue().toString();

                    if (hourIn >= Integer.parseInt(open_time)) {
                        //  Toast.makeText(bookingActivity.this, "Valid date for in time", Toast.LENGTH_SHORT).show();

                        if (hourOut <= Integer.parseInt(close_time)) {

                            if (date_selected == true) {
                                if (minteger >= 1) {
//TODO push hours if valid
                                    MakeBooking(hourIn, hourOut);
                                    hours_selected = true;
                                    getHours(hourIn, hourOut);


                                } else {
                                    Toast.makeText(bookingActivity.this, "No. of people not selected", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(bookingActivity.this, "date not selected", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(bookingActivity.this, name + " closes at " + close_time + ":00", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(bookingActivity.this, name + " opens at - " + open_time + ":00", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(bookingActivity.this, "No values in the DB", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "2:00":
                    hourIn = 2;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "3:00":
                    hourIn = 3;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "4:00":
                    hourIn = 4;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "5:00":
                    hourIn = 5;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "6:00":
                    hourIn = 6;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "7:00":
                    hourIn = 7;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "8:00":
                    hourIn = 8;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "9:00":
                    hourIn = 9;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "10:00":
                    hourIn = 10;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "11:00":
                    hourIn = 11;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "12:00":
                    hourIn = 12;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "13:00":
                    hourIn = 13;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "14:00":
                    hourIn = 14;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "15:00":
                    hourIn = 15;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "16:00":
                    hourIn = 16;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "17:00":
                    hourIn = 17;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "18:00":
                    hourIn = 18;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "19:00":
                    hourIn = 19;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "20:00":
                    hourIn = 20;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "21:00":
                    hourIn = 21;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "22:00":
                    hourIn = 22;
                    VadilateOpenTime(vadilateHourIn,hourIn);
                    break;
                case "23:00":
                    hourIn = 23;
                    VadilateOpenTime(vadilateHourIn,hourIn);

            }

            txtTimein.setText("Time in - " + hourIn + ":00");

        }

        //Time out
        if (itemOut == TimeOut[position].toString()) {

            switch (itemOut) {
                case "1:00 ":
                    hourOut = 1;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "2:00":
                    hourOut = 2;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "3:00":
                    hourOut = 3;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "4:00":
                    hourOut = 4;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "5:00":
                    hourOut = 5;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "6:00":
                    hourOut = 6;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "7:00":
                    hourOut = 7;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "8:00":
                    hourOut = 8;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "9:00":
                    hourOut = 9;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "10:00":
                    hourOut = 10;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "11:00":
                    hourOut = 11;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "12:00":
                    hourOut = 12;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "13:00":
                    hourOut = 13;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "14:00":
                    hourOut = 14;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "15:00":
                    hourOut = 15;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;

                case "16:00":
                    hourOut = 16;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "17:00":

                    hourOut = 17;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "18:00":

                    hourOut = 18;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "19:00":

                    hourOut = 19;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "20:00":

                    hourOut = 20;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;
                case "21:00":
                    hourOut = 21;
                    VadilateCloseTime(vadilateHourOut,hourOut);
                    break;

                case "22:00":
                    hourOut = 22;

                    VadilateCloseTime(vadilateHourOut,hourOut);

                    break;
                case "23:00":
                    hourOut = 23;

                    VadilateCloseTime(vadilateHourOut,hourOut);

            }

            txtTimeOut.setText("Time out - " + hourOut + ":00");
            nameOfPerson.setText("Name :" + mUsername);
            if (date_selected == true) {
                txtDateBooked.setText("Date booked - " + date);
            } else {
                txtDateBooked.setText("Date booked - " + "date not selected ");
            }

            if (minteger >= 1) {
                txtNumberOfppl.setText("Number of people - " + minteger);
            } else {
                txtNumberOfppl.setText("Number of people - " + 0);
            }


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void increase(View view) {

        if (minteger <= 10) {
            minteger = minteger + 1;
            display(minteger);

            //TODO added for increase
            if (hours_selected == true) {
                printPrice(totalPrice * (hourOut - hourIn));
            } else {
                printPrice(totalPrice);
            }


        } else {
            Toast.makeText(this, "cant be more than 11", Toast.LENGTH_SHORT).show();
        }
    }

    public void decrease(View view) {

        if (minteger > 0) {
            minteger = minteger - 1;


            display(minteger);

            //TODO added for decrease
            if (hours_selected == true) {
                printPrice(totalPrice * (hourOut - hourIn));
            } else {
                printPrice(totalPrice);
            }


        } else {
            Toast.makeText(this, "cant be less than zero", Toast.LENGTH_SHORT).show();
        }
    }

    private void display(int number) {
        TextView displayInteger = findViewById(R.id.tvNoOfPpl);
        displayInteger.setText("" + number);

        personNumber = number;
        numberofPeople = Integer.toString(personNumber);

        try {


            totalPrice = Integer.parseInt("" + pricee) * number;


            nameOfPerson.setText("Name :" + mUsername);
            txtTimein.setText("Time in - " + hourIn + ":00");
            txtTimeOut.setText("Time out - " + hourOut + ":00");

            if (date_selected == true) {
                txtDateBooked.setText("Date booked - " + date);
            }
            txtNumberOfppl.setText("Number of people - " + numberofPeople);

            //TODO add logic


            if (hours_selected == true) {
                printPrice(totalPrice * (hourOut - hourIn));
            } else {
                printPrice(totalPrice);
            }

        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
        }

    }


    //hourOut > hourIn
    public void MakeBooking(final int in, final int out) {

        if (out > in) {
            //Making a booking
            mbookingReference = mFirebaseDatabase.getReference().child("booking").child("user_id").child(user_uid);
            mbookingReference.child(name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Bookings bookings = new Bookings(mUsername + "", "" + name, Integer.toString(out - in), Integer.toString(in), Integer.toString(out), date, Integer.toString(personNumber), Integer.toString(totalPrice), pic);

                    String key = mbookingReference.push().getKey();

                    Time_in = Integer.toString(in);
                    Time_out = Integer.toString(out);

                    if (bookBlocker == 0) {
                        mbookingReference.child(key).setValue(bookings);
                        Toast.makeText(bookingActivity.this, "Successfully booked", Toast.LENGTH_SHORT).show();
                        bookBlocker++;

                        Intent i = new Intent(bookingActivity.this, MainMenuFragment.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(bookingActivity.this, "You have already booked", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            Toast.makeText(bookingActivity.this, "Time in must be lesser than time out", Toast.LENGTH_SHORT).show();
        }
    }

    public void printPrice(int price) {

        txtPrice.setText("R " + price);
    }


    public void getHours(int hourIn, int hourOut){
        Toast.makeText(this, "hours - " + (hourOut - hourIn), Toast.LENGTH_SHORT).show();
    }


    public void VadilateOpenTime(int openTime ,int timeSelected){

        if (((openTime == timeSelected) || (openTime<timeSelected))){

            Toast.makeText(this, "Taaaa", Toast.LENGTH_SHORT).show();

        }
        else {

            Toast.makeText(this, "we open at "+openTime+":00", Toast.LENGTH_SHORT).show();
        }
    }

    public void VadilateCloseTime(int closeTime ,int timeSelected){

        if (((closeTime == timeSelected) || (closeTime>timeSelected))){

            Toast.makeText(this, "Taaaa", Toast.LENGTH_SHORT).show();

        }
        else {

            Toast.makeText(this, "we close at "+closeTime +":00", Toast.LENGTH_SHORT).show();
        }
    }
}




