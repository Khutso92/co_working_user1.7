package com.example.khutsomatlala.hackaton_user11.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.Property_DetailsActivity;
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.WorkingSpace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class PropertyAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<WorkingSpace> mValues;
    private Activity activity;

    private FirebaseAuth mAuth;

    public PropertyAdapter(List<WorkingSpace> items, Activity activity) {
        this.mValues = items;
        this.activity = activity;
    }

    @Override
    public MyItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.working_spaces_item_layout, parent, false);
        return new MyItemRecyclerViewAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyItemRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        System.out.println(mValues.get(position).getPlaceDetails().getPlaceName() + " " + mValues.get(position).getPlaceDetails().getPlaceHours());

        //Assigning data
        Glide.with(activity).load(mValues.get(position).getPlaceDetails().getCover_pic());
        holder.hours.setText(mValues.get(position).getPlaceDetails().getPlaceHours() + " - R" + mValues.get(position).getPlaceDetails().getPrice() + " per hour");
        holder.address.setText(mValues.get(position).getPlaceDetails().getPlaceAddress());
        holder.placeName.setText(mValues.get(position).getPlaceDetails().getPlaceName());


        Glide.with(activity).load(mValues.get(position).getPlaceDetails().getCover_pic()).into(holder.pic);

        //System.out.println(mValues.get(position).getPictures().get(0).getImageUrl());

        // System.out.print("Pic --" + mValues.get(position).getPlaceDetails().getCover_pic());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database;
                DatabaseReference myRef;
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference();

                mAuth = FirebaseAuth.getInstance();
                String key = mAuth.getCurrentUser().getUid();


                myRef.child("users").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        String user_name = dataSnapshot.child("name").getValue().toString();

                        Intent intent = new Intent(activity, Property_DetailsActivity.class);


                        String lat = mValues.get(position).getPlaceDetails().getPlaceLatitude();
                        String lon = mValues.get(position).getPlaceDetails().getPlaceLongitude();
                        String name = mValues.get(position).getPlaceDetails().getPlaceName();
                        String call = mValues.get(position).getPlaceDetails().getPlaceCell();
                        String hours = mValues.get(position).getPlaceDetails().getPlaceHours();
                        String address = mValues.get(position).getPlaceDetails().getPlaceAddress();
                        String infor = mValues.get(position).getPlaceDetails().getPlaceInfo();
                        String SpacePic = mValues.get(position).getPlaceDetails().getCover_pic();
                        String email = mValues.get(position).getPlaceDetails().getPlaceWebsite();
                        String price = Long.toString(mValues.get(position).getPlaceDetails().getPrice());

                        intent.putExtra("lat", lat);
                        intent.putExtra("lon", lon);
                        intent.putExtra("name", name);
                        intent.putExtra("call", call);
                        intent.putExtra("infor", infor);
                        intent.putExtra("hours", hours);
                        intent.putExtra("address", address);
                        intent.putExtra("SpacePic", SpacePic);
                        intent.putExtra("price", price);
                        intent.putExtra("email", email);


                        intent.putExtra("user_name", user_name);

                        activity.startActivity(intent);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ImageView pic;
        public TextView hours;
        public TextView price;
        public TextView address;
        public TextView placeName;
        public TextView feat1;
        public TextView feat2;
        public TextView feat3;

        public WorkingSpace mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            pic = view.findViewById(R.id.wsPic);
            hours = view.findViewById(R.id.wsHours);
            price = view.findViewById(R.id.wsPrice);
            address = view.findViewById(R.id.wsAddress);
            placeName = view.findViewById(R.id.wsName);
            feat1 = view.findViewById(R.id.wsFeat1);
            feat2 = view.findViewById(R.id.wsFeat2);
            feat3 = view.findViewById(R.id.wsFeat3);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + placeName.getText() + "'";
        }
    }



}
