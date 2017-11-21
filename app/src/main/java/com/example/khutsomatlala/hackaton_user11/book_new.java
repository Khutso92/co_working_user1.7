package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.model.Bookings;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
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


    private float mTotal = 0;


    Boolean TimeIn = false, TimeOut = false, dateEntered = false, one = false, more = false;

    String pic, name, pricee, mEmail, mUsername;


    TextView placeName, mPrice;
    String dayStamp = new SimpleDateFormat("yyyy - MM - dd").format(new Date());

    //Time and date picker
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();

    Calendar dateTime = Calendar.getInstance();

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


    String in_hour, out_hour;

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
        in_hour = i.getStringExtra("hourIn");
        out_hour = i.getStringExtra("hourOut");

        mPrice = (TextView) findViewById(R.id.txtPrice);


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
                i.putExtra("price", pricee);

                Toast.makeText(book_new.this, "" + date, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionBar.setTitle(dateFormat.format(firstDayOfNewMonth));

            }
        });


    }





}

