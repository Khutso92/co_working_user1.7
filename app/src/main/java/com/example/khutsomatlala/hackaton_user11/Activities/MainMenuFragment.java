package com.example.khutsomatlala.hackaton_user11.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.khutsomatlala.hackaton_user11.R;

public class MainMenuFragment extends AppCompatActivity {

    private TextView mTextMessage;
    String type;
    Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment = new MainActivity();

                    loadFragment(fragment);
                    return true;

                case R.id.navigation_dashboard:

                    fragment = new EventFragment();

                    loadFragment(fragment);
                    return true;

                case R.id.navigation_property:

                    fragment = new PropertyFragment();

                    loadFragment(fragment);
                    return true;

                case R.id.profile:

                    fragment = new ProfileListActivity();

                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MainActivity f = new MainActivity();
        fragmentManager = this.getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, f);
        transaction.commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void fab(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileListActivity.class);
       /* intent.putExtra("user_uid", user_uid);
        intent.putExtra("mUsername", user_name);
        intent.putExtra("email", email);*/

        intent.putExtra("type", type);


        // Toast.makeText(this, "name " + user_name +"\n email" + email   , Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void loadFragment(Fragment fragment) {

        // load fragment

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }

}
