package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khutsomatlala.hackaton_user11.R;

//import com.example.khutsomatlala.hackaton_user11.MainActivity;
//import com.example.khutsomatlala.hackaton_user11.R;
////
//import com.example.khutsomatlala.hackaton_user11.MainActivity;
//import com.example.khutsomatlala.hackaton_user11.R;

public class Splash extends Activity {
    private TextView tv;
    private ImageView iv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv = (ImageView) findViewById(R.id.iv);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
//        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this, MainActivity.class);



        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
