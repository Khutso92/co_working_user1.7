package com.example.khutsomatlala.hackaton_user11;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends Activity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCommentsDatabaseReference,mRateDatabaseReference;
    private String  rateMessage;
    EditText message;
    String PlaceName;
    List<FriendlyMessage> mComments;
    private FirebaseAuth mFirebaseAuth;


    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 140;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private EditText mMessageEditText;
      Button mSendButton ,numberOfUser,numberOfReviews;
      String mUsername;
      String users;
    long reviews;


    //rating
    int mTotalRating = 0;
    long mNumberofUser = 0;
    float mAverage = 0;
    Button ftitle2, ftitle3;
    RatingBar ratingBar;

    RatingBar ratingRatingBar;
    TextView ratingDisplayTextView;
    int RateNumber;
    String NumberofUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        message = (EditText) findViewById(R.id.messageEditText);
        mComments = new ArrayList<>();

        Intent i = getIntent();
        PlaceName = i.getStringExtra("PlaceName");
        users =i.getStringExtra("number");

        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mCommentsDatabaseReference = mFirebaseDatabase.getReference().child("comments");
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);

        numberOfReviews = (Button)findViewById(R.id.reviews);
        numberOfUser = (Button)findViewById(R.id.NumberUsers);

        numberOfUser.setText(""+users);


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
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();


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
                // TODO: Sending data to the DB
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername);

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
//

                //Fectching information from database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    reviews = dataSnapshot.getChildrenCount();

                    numberOfReviews.setText( ""+ reviews);

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
        mRateDatabaseReference = mFirebaseDatabase.getReference();
        ratingRatingBar = findViewById(R.id.rating_rating_bar);
        ratingDisplayTextView = findViewById(R.id.rating_display_text_View);
        ratingRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, float v, boolean b) {
                if (ratingRatingBar.getRating() == 1) {
                    rateMessage = "Hated it";
                    RateNumber = 1;

                } else if ((int) v == 2) {
                    rateMessage = "Disliked it";
                    RateNumber = 2;
                } else if ((int) v == 3) {
                    rateMessage = "It's OK";
                    RateNumber = 3;
                } else if ((int) v == 4) {
                    rateMessage = "Liked it";
                    RateNumber = 4;
                } else {
                    rateMessage = "Loved it";
                    RateNumber = 5;
                }

                ratingDisplayTextView.setText("" + rateMessage);

                //sending the rating
                mRateDatabaseReference.child("Rating").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //mRateDatabaseReference.child("Rating").child(PlaceName).child(mAuth.getCurrentUser().getDisplayName()).setValue(RateNumber);
                         mRateDatabaseReference.child("Rating").child(PlaceName).child("Khutso").setValue(RateNumber);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }


        });

        mRateDatabaseReference.child("Rating").child(PlaceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mNumberofUser = dataSnapshot.getChildrenCount();


                NumberofUser = String.valueOf(mNumberofUser);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    mTotalRating = mTotalRating + Integer.parseInt(snapshot.getValue().toString());
                }
                try {

                    mAverage = (float) ((mTotalRating / mNumberofUser));

                 /*   DecimalFormat decimalFormat = new DecimalFormat("#.##");

                    ftitle2.setText(decimalFormat.format(mAverage) + "\n Stars");*/

                    mTotalRating = 0;

                } catch (ArithmeticException e) {
                    Toast.makeText(ReviewActivity.this, "error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



    }

    public void GoToReview(View view) {

        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("PlaceName", PlaceName);
        intent.putExtra("number", NumberofUser);
        startActivity(intent);
    }
}
