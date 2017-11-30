package com.example.khutsomatlala.hackaton_user11.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model.Profile;
import com.example.khutsomatlala.hackaton_user11.model.ProfilePojo;

import java.util.List;

/**
 * Created by Admin on 11/28/2017.
 */

public class ProfileAdapter extends BaseAdapter {

    Context c;
    List<ProfilePojo> profileList;


    public ProfileAdapter(Profile c, int activity_profile, List<ProfilePojo> profileItems) {
        this.c = c;
        this.profileList = profileItems;

    }


    @Override
    public int getCount() {
        return profileList.size();
    }

    @Override
    public Object getItem(int position) {
        return profileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.activity_profile, parent, false);
        }

        ImageView imgView = convertView.findViewById(R.id.profileBackground);
       /// Button btnUpload = convertView.findViewById(R.id.btnUpload);
        TextView placenameTxt = convertView.findViewById(R.id.profilePlaceName);
        TextView nameTxt = convertView.findViewById(R.id.profileName);
        TextView timeInTxt = convertView.findViewById(R.id.profileTimeIn);
        TextView timeOutTxt = convertView.findViewById(R.id.profileTimeOut);
        TextView dateTxt = convertView.findViewById(R.id.profileDate);
        TextView noOfPplTxt = convertView.findViewById(R.id.profileNoOfPpl);
        TextView priceTxt = convertView.findViewById(R.id.profilePrice);


        //image
        final ProfilePojo s = (ProfilePojo) this.getItem(position);
        Glide.with(c)
                .load(s.getImage())
                .into(imgView);


        placenameTxt.setText("Place name " + s.getPlaceName());
        timeInTxt.setText("Time in " + s.getStart_time() + ":00");
        timeOutTxt.setText("Time out " + s.getEnd_time() + ":00");
        dateTxt.setText("Date selected " + s.getDate());
        noOfPplTxt.setText("No. of people " + s.getNumberOfPeople());
        priceTxt.setText("Total Price R" + s.getPrice());

        return convertView;

    }


}
