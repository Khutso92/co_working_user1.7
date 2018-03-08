package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.adapter.MyItemRecyclerViewAdapter;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.Feature;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.PlaceDetails;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.PlacePicture;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.WorkingSpace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Fragment {


    private DatabaseReference mDatabaseRefDetails;
    private List<WorkingSpace> workingSpaces = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MyItemRecyclerViewAdapter adapter;
    private ProgressDialog progressDialog;
    public static Boolean stauts = false;
    private DatabaseReference user;

    private  View view;
    String user_name, user_uid, email;


    FirebaseAuth mAuth;

    public MainActivity(){}


    private Toolbar toolbarBottomNav;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.activity_main, container, false);


        //set Toobar

        toolbarBottomNav = view.findViewById(R.id.toolbarBottomNav);

        toolbarBottomNav.setTitle("Destination");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarBottomNav);


        mRecyclerView = view.findViewById(R.id.recyclerView);
        mAuth = FirebaseAuth.getInstance();

        user_uid = mAuth.getCurrentUser().getUid();

        mDatabaseRefDetails = FirebaseDatabase.getInstance().getReference("new_places");

        //Init adapter
        adapter = new MyItemRecyclerViewAdapter(workingSpaces, getActivity());
        mRecyclerView.setAdapter(adapter);


        mDatabaseRefDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  progressDialog.dismiss();
                workingSpaces.clear();

                //Fetching information from database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WorkingSpace workingSpace = new WorkingSpace();

                    PlaceDetails details = new PlaceDetails();
                    details.setPlaceAddress(snapshot.child("details").child("PlaceAddress").getValue().toString());
                    details.setPlaceName(snapshot.child("details").child("PlaceName").getValue().toString());
                    details.setPlaceCell(snapshot.child("details").child("PlaceCell").getValue().toString());
                    details.setPlaceInfo(snapshot.child("details").child("PlaceInfo").getValue().toString());
                    details.setPlaceHours(snapshot.child("details").child("PlaceHours").getValue().toString());
                    details.setPrice((long) snapshot.child("details").child("PlacePrice").getValue());
                    details.setPlaceLatitude(snapshot.child("details").child("Latitude").getValue().toString());
                    details.setPlaceLongitude(snapshot.child("details").child("Longitude").getValue().toString());
               // details.setPlaceWebsite(snapshot.child("details").child("PlaceWebsite").getValue().toString());
                    details.setCover_pic(snapshot.child("details").child("cover_pic").getValue().toString());

                    workingSpace.setPlaceDetails(details);
                    List<PlacePicture> pictures = new ArrayList<>();

                    //Get and set pictures of working space
                    for (DataSnapshot snap : snapshot.child("pictures").getChildren()) {
                        PlacePicture picture = new PlacePicture();
                        picture.setImageUrl(snap.child("url").getValue().toString());
                        pictures.add(picture);
                    }

                    workingSpace.setPictures(pictures);
                    //Get and set Amenities of working space
                    List<Feature> features = new ArrayList<>();
                    for (DataSnapshot _snap : snapshot.child("Amenities").getChildren()) {
                        Feature feature = new Feature();
                        feature.setTitle(_snap.child("title").getValue().toString());
                        feature.setImageUrl(_snap.child("image").getValue().toString());
                        features.add(feature);
                    }
                    workingSpace.setFeatures(features);
                    workingSpaces.add(workingSpace);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        user = FirebaseDatabase.getInstance().getReference().child("users").child(user_uid);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_name = dataSnapshot.child("name").getValue().toString();
                email = dataSnapshot.child("email").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

return view;
    }




}


