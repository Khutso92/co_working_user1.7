package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.khutsomatlala.hackaton_user11.Activities.Third_Host;

public class Property_Questions extends AppCompatActivity {

    Button btn_next_property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property__questions);

        btn_next_property = findViewById(R.id.btn_next_property);

        btn_next_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Property_Questions.this, Third_Host.class);
                startActivity(i);
            }
        });
    }
}
