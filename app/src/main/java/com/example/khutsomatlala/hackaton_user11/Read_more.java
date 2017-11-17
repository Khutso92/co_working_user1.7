package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Read_more extends AppCompatActivity {
    TextView tvReadmore;
    String infor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more);

        tvReadmore = (TextView) findViewById(R.id.readMore);

        Intent intent = getIntent();
        infor = intent.getStringExtra("infor");
        tvReadmore.setText(infor);
    }

}
