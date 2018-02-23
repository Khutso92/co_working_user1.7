package com.example.khutsomatlala.hackaton_user11.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khutsomatlala.hackaton_user11.R;


public class PropertyFragment extends Fragment {

  private TextView textView;
  private View view;
    public PropertyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_property, container, false);
    textView = view.findViewById(R.id.blank);

        return view;
    }


}
