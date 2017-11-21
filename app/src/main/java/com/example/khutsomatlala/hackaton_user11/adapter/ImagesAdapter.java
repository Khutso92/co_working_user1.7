package com.example.khutsomatlala.hackaton_user11.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model.slideImagePojo;

import java.util.List;

/**
 * Created by Admin on 17/11/2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder>  {



    private Activity context;
    private List<slideImagePojo> catalogList;
    private List<slideImagePojo> catalogss;
    private Activity applicationContext;
    private slideImagePojo catalog;

    public ImagesAdapter(Activity context, List<slideImagePojo> catalogList) {
        this.context = context;
        this.catalogList = catalogList;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_catalog,false);
//        .inflate(R.layout.card_listitem, parent, false);
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slidemodel, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final slideImagePojo catalog = catalogList.get(position);

        Glide.with(context)
                .load(catalog.getImage())
                .into(holder.imageButton1);




    }

    @Override
    public int getItemCount() {
        return (null != catalogList ? catalogList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageButton1 , imb2,imb1 ;


        public MyViewHolder(View itemView) {
            super(itemView);

            imageButton1 = itemView.findViewById(R.id.imageslide);


//                        //FUEL ICON COLOR CHANGE

        }
    }






}