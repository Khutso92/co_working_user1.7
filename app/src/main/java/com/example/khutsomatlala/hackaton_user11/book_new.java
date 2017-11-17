package com.example.khutsomatlala.hackaton_user11;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.model.Bookings;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 9/15/2017.
 */

public class book_new extends AppCompatActivity {


    //time and date memebers

    private int mHoursIn, mHoursOut, mMinsOut, mMinsIn, mDay, mMonth, mYear, mDiffMins;

    private float mTotal = 0;

    String duration;

    Boolean TimeIn = false, TimeOut = false, dateEntered = false, one = false, more = false;

    String pic, name, pricee, mTimeIn, mTimeOut, mDate, mEmail, mUsername;


    TextView placeName, mPrice, tv_date, txtTotalPrice;
    String dayStamp = new SimpleDateFormat("yyyy - MM - dd").format(new Date());

    //Time and date picker
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();

    Calendar dateTime = Calendar.getInstance();

    private TextView text;

    private TextView textout;

    private Button btn_date, btn_time, btn_time_out;


    //Number of people
    RadioButton MorePeople, onePerson;
    RadioGroup radioGroup;
    EditText PeopleNumber;
    String numberOfPeople, user_uid;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mbookingReference;

    int hourOfDay;
    int minute;
    int second;

    boolean btnTimein = false, btnTimeOut = true;
    Dialog dialog;
    int minteger = 0;



    String month, year, day, date;
    //Calendar
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());


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




        mPrice = (TextView) findViewById(R.id.txtPrice);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotalPrice);
        MorePeople = (RadioButton) findViewById(R.id.rb_MorePeople);
        onePerson = (RadioButton) findViewById(R.id.rb_onePerson);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        PeopleNumber = (EditText) findViewById(R.id.edtPeopleNumber);


        //Get a new instance of Calendar
        final Calendar c = Calendar.getInstance();
        hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
        minute = c.get(c.MINUTE); //Current Minute
        second = c.get(c.SECOND); //Current Second


        //Calendar
        final Calendar cal = Calendar.getInstance();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        Event ev1 = new Event(Color.RED, 1510305111000l, "Personal date");
        compactCalendarView.addEvent(ev1);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                year = "20" + (dateClicked.getYear() - 100);

                day = "" + dateClicked.getDate();

                month = "" + (dateClicked.getMonth() + 1);

                date = day + "-" + month + "-" + year;

                Intent i = new Intent(book_new.this, Time_picker.class);

                i.putExtra("placeName", name);
                i.putExtra("date", date);
                startActivity(i);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionBar.setTitle(dateFormat.format(firstDayOfNewMonth));

            }
        });


    }


    public void email(View view) {

        mbookingReference = mFirebaseDatabase.getReference().child("bookings").child(name);

        mbookingReference.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Bookings bookings = new Bookings("names", "2", "07:00", "08:00", "14 - 11-17");

                String key = mbookingReference.push().getKey();

                mbookingReference.child(key).setValue(bookings);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }





    public void increase(View view) {

        minteger = minteger + 1;
        display(minteger);

    }public void decrease(View view) {

        if (minteger > 0) {
            minteger = minteger - 1;
            display(minteger);
        }
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.tvNoOfPpl);
        displayInteger.setText("" + number);
    }

}

