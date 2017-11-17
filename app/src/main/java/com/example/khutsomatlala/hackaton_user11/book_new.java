package com.example.khutsomatlala.hackaton_user11;

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
import android.widget.ImageView;
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
    String date;

    String duration;

    Boolean TimeIn = false, TimeOut = false, dateEntered = false, one = false, more = false;

    String pic, name, pricee, mTimeIn, mTimeOut, mDate, mEmail, mUsername;

    ImageView BookPic;
    int PushControl = 1;


    TextView mPrice, txtTotalPrice;

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


    String hourIn, hourOut;

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


        Intent u = getIntent();

        //from the timepicker
        hourIn = u.getStringExtra("hourIn");
        hourOut = u.getStringExtra("hourOut");


        Toast.makeText(this, "Hour in" + hourIn + "\nHour Out" + hourOut, Toast.LENGTH_SHORT).show();


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

        Event ev1 = new Event(Color.GREEN, 1510305111000l, "Personal date");
        compactCalendarView.addEvent(ev1);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDayClick(Date dateClicked) {

                date = dateClicked.getDate() + "-" + Integer.toString(dateClicked.getMonth() + 1) + "-20" + Integer.toString(dateClicked.getYear() - 100);

                Toast.makeText(book_new.this, "" + date, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(book_new.this, Time_picker.class);

                i.putExtra("date", date);
                i.putExtra("placeName", name);
                startActivity(i);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionBar.setTitle(dateFormat.format(firstDayOfNewMonth));

            }
        });


    }


    public void email(View view) {


        try {

            mbookingReference = mFirebaseDatabase.getReference().child("bookings").child(name);
            mbookingReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Bookings bookings = new Bookings("names", "2", "07:00", "08:00", "14 - 11-17");

                    if (PushControl == 1) {
                        mbookingReference.push().setValue(bookings);
                        PushControl++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e)

        {

            Toast.makeText(this, " Something went wrong with the booking", Toast.LENGTH_SHORT).show();
        }


    }


    public void NumPeople(View view) {

        int selectedId = radioGroup.getCheckedRadioButtonId();


        switch (selectedId) {

            case R.id.rb_MorePeople:
                txtTotalPrice.setText("R0.00");

                mTotal = 0;
                more = true;
                if (dateEntered) {
                    if (TimeIn && TimeOut) {

                        mDiffMins = mMinsIn + mMinsOut;

                        duration = String.valueOf(mHoursOut - mHoursIn);

                        if (mDiffMins >= 60) {

                            mTotal = (mHoursOut - mHoursIn) + 1;

                        } else {

                            mTotal = (mHoursOut - mHoursIn);

                        }

                        mTotal = mTotal * Integer.parseInt(pricee);

                        numberOfPeople = PeopleNumber.getText().toString();


                        if (!TextUtils.isEmpty(numberOfPeople)) {
                            txtTotalPrice.setText("R" + Math.abs(mTotal) * Integer.parseInt(numberOfPeople) + "0");
                        } else {
                            Toast.makeText(this, "enter number of people", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Enter time in and out", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.rb_onePerson:
                one = true;
                mTotal = 0;

                numberOfPeople = "1";

                if (TimeIn && TimeOut) {

                    mDiffMins = mMinsIn + mMinsOut;

                    duration = String.valueOf(mHoursOut - mHoursIn);

                    if (mDiffMins >= 60) {

                        mTotal = (mHoursOut - mHoursIn) + 1;

                    } else {

                        mTotal = (mHoursOut - mHoursIn);

                    }

                    mTotal = mTotal * Integer.parseInt(pricee);

                    txtTotalPrice.setText("R" + Math.abs(mTotal) + "0");
                } else {
                    Toast.makeText(this, "Enter time in and out", Toast.LENGTH_SHORT).show();
                }


                break;

        }

    }

}

