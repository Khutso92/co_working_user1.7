package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.adapter.MessageAdapter;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.FriendlyMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewActivity extends Activity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCommentsDatabaseReference;

    EditText message;
    String PlaceName, user_uid;
    List<FriendlyMessage> mComments;
    private FirebaseAuth mFirebaseAuth;

    DatabaseReference db;
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 140;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private EditText mMessageEditText;
    Button mSendButton, numberOfUser, numberOfReviews;
    String mUsername;
    String user_name;
    long reviews;
    TextView tv_view;


    int minute, hour;
    CircleImageView profilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        message = (EditText) findViewById(R.id.messageEditText);
        mComments = new ArrayList<>();

        Intent i = getIntent();
        user_name = i.getStringExtra("user_name");
        PlaceName = i.getStringExtra("PlaceName");
        user_uid = i.getStringExtra("user_uid");


        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCommentsDatabaseReference = mFirebaseDatabase.getReference().child("comments");
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        profilePicture = findViewById(R.id.profilePicMessage);
        tv_view = findViewById(R.id.tv_review);

        //  numberOfUser.setText(""+users);


        // Initialize message ListView and its adapter
        final List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);


        //   again check if the user is already logged in or not
        if (mFirebaseAuth.getCurrentUser() == null) {

//            User not logged in
            finish();
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
        }

        //  fetch and  display the user details

        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (user != null) {

            // Name, email address, and profile photo Url
            String name = user.getDisplayName();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.

            String uid = user.getUid();

            mUsername = name;
        }


        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mComments.clear();

                GregorianCalendar date = new GregorianCalendar();
                minute = date.get(Calendar.MINUTE);
                hour = date.get(Calendar.HOUR);

                String hourText = "";
                String minuteText;

                switch (minute) {
                    case 0:
                        minuteText = "00";
                        break;
                    case 1:
                        minuteText = "01";
                        break;
                    case 2:
                        minuteText = "02";
                        break;
                    case 3:
                        minuteText = "03";
                        break;
                    case 4:
                        minuteText = "04";
                        break;
                    case 5:
                        minuteText = "05";
                        break;
                    case 6:
                        minuteText = "06";
                        break;
                    case 7:
                        minuteText = "07";
                        break;
                    case 8:
                        minuteText = "08";
                        break;
                    case 9:
                        minuteText = "09";
                        break;
                    default:
                        minuteText = String.valueOf(minute);

                }

                switch (hour) {
                    case 0:
                        hourText = "12";
                        break;
                    case 1:
                        hourText = "01";
                        break;
                    case 2:
                        hourText = "02";
                        break;
                    case 3:
                        hourText = "03";
                        break;
                    case 4:
                        hourText = "04";
                        break;
                    case 5:
                        hourText = "05";
                        break;
                    case 6:
                        hourText = "06";
                        break;
                    case 7:
                        hourText = "07";
                        break;
                    case 8:
                        hourText = "08";
                        break;
                    case 9:
                        hourText = "09";
                        break;
                    default:
                        hourText = String.valueOf(hour);

                }

                // TODO: Sending data to the DB
                //FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), user_name, hour + ":" + minute);
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), user_name, String.valueOf(hourText) + ":" + minuteText);

                String key = mCommentsDatabaseReference.push().getKey();
                mCommentsDatabaseReference.child(PlaceName).child(key).setValue(friendlyMessage);



                // Clear input box
                mMessageEditText.setText("");
            }
        });

        // mCommentsDatabaseReference.addValueEventListener(new ValueEventListener()
        mCommentsDatabaseReference.child(PlaceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //Fectching information from database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    reviews = dataSnapshot.getChildrenCount();


                    if(reviews >0){

                        tv_view.setVisibility(View.GONE);
                    }

                    final FriendlyMessage friendlyMessage = snapshot.getValue(FriendlyMessage.class);
                    mComments.add(friendlyMessage);


                }

                //Init adapter
                mMessageAdapter = new MessageAdapter(ReviewActivity.this, R.layout.image_item, mComments);

                //
                mMessageListView.setAdapter(mMessageAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void GoToReview(View view) {

        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("PlaceName", PlaceName);
        startActivity(intent);
    }
}
