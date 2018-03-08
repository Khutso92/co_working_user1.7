package com.example.khutsomatlala.hackaton_user11.Activities;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfileListActivity extends Fragment {


    private static final int RESULT_OK = 1;
    String user_uid, mUsername, email, type;
    FirebaseAuth mAuth;

    StorageReference childRef;
    Button btnUpload, btnHost, btnAdd,btnBookings,btnSignout;

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


    View view;

    public ProfileListActivity() {
    }

    @SuppressLint("WrongViewCast")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_profile_list, container, false);
        super.onCreate(savedInstanceState);


        Intent i = new Intent();
        mAuth = FirebaseAuth.getInstance();
        mUsername = i.getStringExtra("mUsername");

        user_uid = i.getStringExtra("user_uid");
        email = i.getStringExtra("email");
        type = i.getStringExtra("type");

        btnUpload = view.findViewById(R.id.btnUpload);
        btnHost = view.findViewById(R.id.btn_host);
        btnAdd = view.findViewById(R.id.Add);
        btnBookings = view.findViewById(R.id.btnBookings);
        btnSignout = view.findViewById(R.id.btnSignout);
        profilePicture = view.findViewById(R.id.profilePic);
        tv_user_email = view.findViewById(R.id.TextView_profileEmail);
        tv_user_name = view.findViewById(R.id.TextView_profileName);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadProfilePic();
            }
        });

        btnBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToProfile();
            }
        });
        btnHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToHost();
            }
        });
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });


        mStorage = FirebaseStorage.getInstance().getReference();
        databaseProfile = FirebaseDatabase.getInstance().getReference("profile").child("eiWnjD8H3WeglN0un0j0jmc8CuJ2");



       /* if (type.equals("cws")){
            btnHost.setText("Host a co working space");
        }
        else {
            btnHost.setText("Host a "+type);
        }*/
        profile = new ArrayList<>();

        //profile
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        db = mFirebaseDatabase.getReference();


        db.child("profile").child("eiWnjD8H3WeglN0un0j0jmc8CuJ2").child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    Glide.with(getActivity())
                            .load(dataSnapshot.getValue().toString())
                            .centerCrop()
                            .override(100, 100)
                            .into(profilePicture);

                } catch (Exception e) {

                    Toast.makeText(getActivity(), "no picture", Toast.LENGTH_SHORT).show();

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


                //Fectching information from database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    reviews = dataSnapshot.getChildrenCount();

                    ProfilePojo profilePojo = snapshot.getValue(ProfilePojo.class);


                    profile.add(profilePojo);
                    //Init adapter
                    mProfileAdapter = new ProfileAdapter(getActivity(), R.layout.activity_profile, profile);

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
        return view;


    }



    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            // UploadProfilePic();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);

                //Setting image to ImageView
                profilePicture.setImageBitmap(bitmap);
                selected = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                profilePicture.setImageURI(filePath);
            }

        }


    }

    public void btnAdd() {
        isClick = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);

        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
        btnUpload.setVisibility(View.VISIBLE);


    }


    public void UploadProfilePic() {


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

                    Toast.makeText(getActivity(), "Upload successful ", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();

                    Toast.makeText(getActivity(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });

            pd = new ProgressDialog(getActivity());
            pd.setMessage("loading");
            pd.show();

        } catch (NullPointerException f) {
            Toast.makeText(getActivity(), "unable to upload picture", Toast.LENGTH_SHORT).show();
        }
    }



    public void GoToProfile() {

        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra("user_uid", user_uid);
        // Toast.makeText(this, "name " + user_name +"\n email" + email   , Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void SignOut() {

        mAuth.signOut();
        Intent i = new Intent(getActivity(), Auth_loginActivity.class);
        startActivity(i);

    }


    public void GoToHost() {

        Intent i = new Intent(getActivity(), First_Host.class);
        i.putExtra("name", mUsername);


        startActivity(i);

    }


}
