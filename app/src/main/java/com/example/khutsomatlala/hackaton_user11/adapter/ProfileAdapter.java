package com.example.khutsomatlala.hackaton_user11.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.ProfilePojo;

import java.util.List;

/**
 * Created by Admin on 11/28/2017.
 */

public class ProfileAdapter extends BaseAdapter {

    Context c;
    List<ProfilePojo> profileList;


    public ProfileAdapter(Context c, int resource, List<ProfilePojo> profileItems) {
        this.c = c;
        this.profileList = (List<ProfilePojo>) profileItems;


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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //if (convertView != null) {
        convertView = LayoutInflater.from(c).inflate(R.layout.activity_profile, parent, false);
        //     }

        //ImageView imgView = convertView.findViewById(R.id.profilePic);
        /// Button btnUpload = convertView.findViewById(R.id.btnUpload);
        TextView placenameTxt = convertView.findViewById(R.id.profilePlaceName);
        TextView timeInTxt = convertView.findViewById(R.id.profileTimeIn);
        TextView timeOutTxt = convertView.findViewById(R.id.profileTimeOut);
        TextView dateTxt = convertView.findViewById(R.id.profileDate);
        TextView noOfPplTxt = convertView.findViewById(R.id.profileNoOfPpl);
        TextView priceTxt = convertView.findViewById(R.id.profilePrice);
        ImageView spacePic = convertView.findViewById(R.id.iv_spacePic);

        RelativeLayout profileGridView = convertView.findViewById(R.id.linearLayout);

        final ProfilePojo s = (ProfilePojo) this.getItem(position);

        placenameTxt.setText(s.getPlaceName());
        timeInTxt.setText("Time in " + s.getStart_time() + ":00");
        timeOutTxt.setText("Time out " + s.getEnd_time() + ":00");
        dateTxt.setText(s.getDate_booked());
        noOfPplTxt.setText("No. of people " + s.getNumberOfPeople());
        //priceTxt.setText("Total Price R" + s.getPrice());
        String placePic = s.getPlace_image();

        Glide.with(c)
                .load(placePic)
                .centerCrop()
                .into(spacePic);


        profileGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(c);


                // final View dialogView = inflater.inflate(R.layout.custom_dialog_profile, null);


                View dialogView = LayoutInflater.from(c).inflate(R.layout.custom_dialog_profile, parent, false);

                ImageView placePic = dialogView.findViewById(R.id.GridPlacePic);
                TextView placeName = dialogView.findViewById(R.id.GridPlaceName);
                TextView timeIn = dialogView.findViewById(R.id.GridTimeIn);
                TextView timeOut = dialogView.findViewById(R.id.GridTimeOut);
                TextView date = dialogView.findViewById(R.id.GridDate);
                TextView peopleNumber = dialogView.findViewById(R.id.GridNumberOfPeople);
                TextView TotalPrice = dialogView.findViewById(R.id.GridTotalPrice);

                String pic = s.getPlace_image();

                Glide.with(c)
                        .load(pic)
                        .centerCrop()
                        .into(placePic);

                placeName.setText( s.getPlaceName());
                timeIn.setText("time in " + s.getStart_time() +":00");
                timeOut.setText("time out " + s.getEnd_time()+":00");
                date.setText("Date booked " + s.getDate_booked());
                peopleNumber.setText("Number of people " + s.getNumberOfPeople());
                TotalPrice.setText("Total price  R" + s.getPrice() );


                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();

                alertDialog.show();

            }
        });

        return convertView;
    }


}
