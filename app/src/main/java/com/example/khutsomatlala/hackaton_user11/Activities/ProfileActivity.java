package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.adapter.ProfileAdapter;
import com.example.khutsomatlala.hackaton_user11.model.ProfilePojo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    String timeOut, timeIn, placeName, noOfPeople, price, date, user_uid, mUsername ,email;

    TextView profilePlaceName, profileName, profiletimeIn, profiletimeOut, profileDate, profileNoOfPpl, profilePrice;
    StorageReference childRef;
    Button btnUpload, btnPlus;

    CircleImageView profilePicture;
    //profile adapter
    DatabaseReference db;
    Boolean selected = false;
    ArrayList<ProfilePojo> profileList = new ArrayList<>();

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

        profileListView = (ListView) findViewById(R.id.profileGridView);
        final List<ProfilePojo> profilePojos = new ArrayList<>();
        mProfileAdapter = new ProfileAdapter(this, R.layout.activity_profile, profilePojos);
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
        databaseProfile = FirebaseDatabase.getInstance().getReference("profile").child(user_uid);
        //btnUpload.setVisibility(View.GONE);

/*

            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        btnUpload.setVisibility(View.GONE);


                        //uploading the image
                        UploadTask uploadTask = childRef.putFile(filePath);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                @SuppressWarnings("VisibleForTests") Uri uir = taskSnapshot.getDownloadUrl();

                                ProfilePojo profilePojo = new ProfilePojo();

                                profilePojo.setImage(uir.toString());

//            profilePojo.setStuffNo(stuffNo);

//                            FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();


                                if (uir != null) {
                                    childRef = mStorage.child("ProfileImage").child(filePath.getLastPathSegment());
                                    databaseProfile.setValue(profilePojo);
                                }


                                Toast.makeText(ProfileActivity.this, "Upload successful ", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ProfileActivity.this, "Upload Failed -> " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                }
            });
*/



        //profile
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        db = mFirebaseDatabase.getReference();

        db.child("profile").child(user_uid).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChildren()) {
                    Glide.with(getApplicationContext())
                            .load(dataSnapshot.getValue().toString())
                            .centerCrop()
                            .override(300, 150)
                            .into(profilePicture);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // mCommentsDatabaseReference.addValueEventListener(new ValueEventListener()
        db.child("booking").child("user_id").child(user_uid).addValueEventListener(new ValueEventListener() {
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


        tv_user_email.setText(email);
        tv_user_name.setText(mUsername  );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
           // UploadProfilePic();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                profilePicture.setImageBitmap(bitmap);
                selected = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            profilePicture.setImageURI(filePath);
        }

    }

    public void btnAdd(View view) {
        isClick = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    //    btnUpload.setVisibility(View.VISIBLE);

    }


    public void UploadProfilePic(){


        //uploading the image
        UploadTask uploadTask = childRef.putFile(filePath);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                @SuppressWarnings("VisibleForTests") Uri uir = taskSnapshot.getDownloadUrl();

                ProfilePojo profilePojo = new ProfilePojo();

                profilePojo.setImage(uir.toString());

//            profilePojo.setStuffNo(stuffNo);

//                            FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();


                if (uir != null) {
                    childRef = mStorage.child("ProfileImage").child(filePath.getLastPathSegment());
                    databaseProfile.setValue(profilePojo);
                }


                Toast.makeText(ProfileActivity.this, "Upload successful ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(ProfileActivity.this, "Upload Failed -> " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
