package com.example.khutsomatlala.hackaton_user11.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.adapter.ProfileAdapter;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.ProfilePojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 111;
    StorageReference mStorage;

    Uri filePath;
    DatabaseReference databaseProfile;
    ImageView profilePic;
    boolean isClick = false;
    private FirebaseAuth mAuth;
    String timeOut, timeIn, placeName, noOfPeople, price, date, user_uid, mUsername ,email;

    TextView profilePlaceName, profileName, profiletimeIn, profiletimeOut, profileDate, profileNoOfPpl, profilePrice;
    StorageReference childRef;
    Button btnUpload, btnPlus;

    CircleImageView profilePicture;
    //profile adapter
    DatabaseReference db;
    Boolean selected = false;

    ProgressDialog pd;
    String profileUri;

    List<ProfilePojo> profile;
    ListView profileListView;
    public ProfileAdapter mProfileAdapter;
    long reviews;
    private FirebaseDatabase mFirebaseDatabase;

    TextView tv_user_email,tv_user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_profile);



        profileListView =   findViewById(R.id.profileGridView);
        final List<ProfilePojo> profilePojos = new ArrayList<>();
        mProfileAdapter = new ProfileAdapter(ProfileActivity.this,R.layout.activity_profile,profilePojos);
        profileListView.setAdapter(mProfileAdapter);
        profile = new ArrayList<>();

        profilePic =   findViewById(R.id.profilePic);
        btnUpload =  findViewById(R.id.btnUpload);
        tv_user_email = findViewById(R.id.TextView_profileEmail);
        tv_user_name = findViewById(R.id.TextView_profileName);
        profilePicture =  findViewById(R.id.profilePic);

        Intent i = getIntent();

        mUsername = i.getStringExtra("mUsername");
        timeOut = i.getStringExtra("timeOut");
        timeIn = i.getStringExtra("timeIn");
        placeName = i.getStringExtra("placeName");
        noOfPeople = i.getStringExtra("noOfPeople");
        price = i.getStringExtra("price");
        date = i.getStringExtra("date");
        user_uid = i.getStringExtra("user_uid");
        email = i.getStringExtra("email");

        profilePlaceName = findViewById(R.id.profilePlaceName);
        profileName = findViewById(R.id.profileName);
        profiletimeIn = findViewById(R.id.profileTimeIn);
        profiletimeOut = findViewById(R.id.profileTimeOut);
        profileDate = findViewById(R.id.profileDate);
        profileNoOfPpl = findViewById(R.id.profileNoOfPpl);
        profilePrice = findViewById(R.id.profilePrice);
        mStorage = FirebaseStorage.getInstance().getReference();
        databaseProfile = FirebaseDatabase.getInstance().getReference("profile").child("eiWnjD8H3WeglN0un0j0jmc8CuJ2");




        // mCommentsDatabaseReference.addValueEventListener(new ValueEventListener()
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        db = mFirebaseDatabase.getReference();
        db.child("booking").child("user_id").child("eiWnjD8H3WeglN0un0j0jmc8CuJ2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile.clear();
//
                //Fectching information from database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    reviews = dataSnapshot.getChildrenCount();

                    ProfilePojo profilePojo = snapshot.getValue(ProfilePojo.class);


                    profile.add(profilePojo);
                    //Init adapter
                    mProfileAdapter = new ProfileAdapter(ProfileActivity.this, R.layout.activity_profile, profile);

                    //
                    profileListView.setAdapter(mProfileAdapter);

                    //profileName.setText(mUsername);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


}
