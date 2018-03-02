package com.example.khutsomatlala.hackaton_user11.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.R;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {
    Context c;
    private List<String> _imagePaths;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context c, List<String> imagePaths) {
        this._imagePaths = imagePaths;
        this.c = c;
    }

    @Override    public int getCount() {
        return this._imagePaths.size();
    }

    @Override    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;

        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.slide_layout, container,
                false);

        imgDisplay =  viewLayout.findViewById(R.id.slide_images);

        Glide.with(c).load(_imagePaths.get(position)).into(imgDisplay);
        (container).addView(viewLayout);

        return viewLayout;
    }

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);

    }
}