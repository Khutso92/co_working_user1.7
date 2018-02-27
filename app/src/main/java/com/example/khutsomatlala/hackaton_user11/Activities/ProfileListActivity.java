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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.adapter.ProfileAdapter;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.ProfilePojo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

public class ProfileListActivity extends AppCompatActivity {


    String user_uid, mUsername, email,type;
    FirebaseAuth mAuth;

    StorageReference childRef;
    Button btnUpload, btnHost;

    CircleImageView profilePicture;
    //profile adapter
    DatabaseReference db;
    Boolean selected = false;

    ProgressDialog pd;
    String profileUri;
    private int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    boolean isClick = false;
    StorageReference mStorage;
    DatabaseReference databaseProfile;
    private FirebaseDatabase mFirebaseDatabase;
    List<ProfilePojo> profile;
    public ProfileAdapter mProfileAdapter;
    long reviews;
    TextView tv_user_email, tv_user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        Intent i = getIntent();
        mAuth = FirebaseAuth.getInstance();
        mUsername = i.getStringExtra("mUsername");

        user_uid = i.getStringExtra("user_uid");
        email = i.getStringExtra("email");
        type = i.getStringExtra("type");

        btnUpload = findViewById(R.id.btnUpload);
        btnHost = findViewById(R.id.btn_host);
        profilePicture = findViewById(R.id.profilePic);
        tv_user_email = findViewById(R.id.TextView_profileEmail);
        tv_user_name = findViewById(R.id.TextView_profileName);

        mStorage = FirebaseStorage.getInstance().getReference();
        databaseProfile = FirebaseDatabase.getInstance().getReference("profile").child("eiWnjD8H3WeglN0un0j0jmc8CuJ2");



        if (type.equals("cws")){
            btnHost.setText("Host a co working space");
        }
        else {
            btnHost.setText("Host a "+type);
        }
        profile = new ArrayList<>();

        //profile
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        db = mFirebaseDatabase.getReference();

        db.child("profile").child("eiWnjD8H3WeglN0un0j0jmc8CuJ2").child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    Glide.with(getApplicationContext())
                            .load(dataSnapshot.getValue().toString())
                            .centerCrop()
                            .override(100, 100)
                            .into(profilePicture);

                } catch (Exception e) {

                    Toast.makeText(ProfileListActivity.this, "no dp", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // mCommentsDatabaseReference.addValueEventListener(new ValueEventListener()
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
                    mProfileAdapter = new ProfileAdapter(ProfileListActivity.this, R.layout.activity_profile, profile);

                    //
                    //profileListView.setAdapter(mProfileAdapter);

                    //profileName.setText(mUsername);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tv_user_email.setText(email);
        tv_user_name.setText(mUsername);

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
        btnUpload.setVisibility(View.VISIBLE);


    }


    public void UploadProfilePic(View view) {


        //uploading the image
       /* UploadTask uploadTask = childRef.putFile(filePath);

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
        });*/
        btnUpload.setVisibility(View.GONE);

       try {
           childRef = mStorage.child("ProfileImage").child(filePath.getLastPathSegment());

            //uploading the image
            UploadTask uploadTask = childRef.putFile(filePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();

                    @SuppressWarnings("VisibleForTests") Uri uir = taskSnapshot.getDownloadUrl();
                    profileUri = uir.toString();

                    ProfilePojo profilePojo = new ProfilePojo();
                    profilePojo.setImage(uir.toString());
                    databaseProfile.setValue(profilePojo);

                    Toast.makeText(ProfileListActivity.this, "Upload successful ", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();

                    Toast.makeText(ProfileListActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });

            pd = new ProgressDialog(ProfileListActivity.this);
            pd.setMessage("loading");
            pd.show();

       }
       catch (NullPointerException f){
           Toast.makeText(this, "unable to upload pro pic", Toast.LENGTH_SHORT).show();
       }
    }

    public void GoToProfile(View view) {

        Intent intent = new Intent(ProfileListActivity.this, ProfileActivity.class);
        intent.putExtra("user_uid", user_uid);
        // Toast.makeText(this, "name " + user_name +"\n email" + email   , Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void SignOut(View view) {

        mAuth.signOut();
        Intent i = new Intent(ProfileListActivity.this, Auth_loginActivity.class);
        startActivity(i);

    }


    public void GoToHost(View view) {

        Intent i = new Intent(ProfileListActivity.this, First_Host.class);
        i.putExtra("name",mUsername);


        startActivity(i);

    }


}
