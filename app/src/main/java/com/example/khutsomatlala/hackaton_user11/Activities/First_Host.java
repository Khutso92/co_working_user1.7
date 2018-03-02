package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.khutsomatlala.hackaton_user11.Activity_Host;
import com.example.khutsomatlala.hackaton_user11.R;

/**
 * Created by Admin on 1/19/2018.
 */

public class First_Host extends Activity {

    Button btn_first_host;
    TextView tv_name;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_slide);

        btn_first_host = findViewById(R.id.btn_next_first);
        tv_name = findViewById(R.id.tv_name);

        Intent i = getIntent();
        name = i.getStringExtra("name");

        tv_name.setText(name);

        btn_first_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(First_Host.this, Activity_Host.class);
                startActivity(i);
            }
        });

    }


}
