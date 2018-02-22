package com.example.khutsomatlala.hackaton_user11.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.userInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthActivity extends Activity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    DatabaseReference myRef;
    String getname,getEmail, getPassword;

    private EditText email;
    private EditText password;

    private EditText name;
    private EditText surname;
    private EditText cell;


    private Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();


        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);

        name = (EditText) findViewById(R.id.edt_name);
        surname = (EditText) findViewById(R.id.edt_surname);
        cell = (EditText) findViewById(R.id.edt_cell);

        signIn = (Button) findViewById(R.id.btn_sign_in);
        signUp = (Button) findViewById(R.id.btn_sign_up);


//        Checks if user is already logged in
        if (mAuth.getCurrentUser() != null) {

//            User not logged in
            finish();
            startActivity(new Intent(getApplicationContext(), Splash.class));
        }


       /* signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = email.getText().toString().trim();
                String getPassword = password.getText().toString().trim();

                if (!validateSign()) {

                    return;
                } else {

                    callSignIn(getEmail, getPassword);
                }
            }
        });*/


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getEmail = email.getText().toString().trim();
                String getPassword = password.getText().toString().trim();

                createAccount(getEmail, getPassword);
                callSignIn(getEmail, getPassword);
            }
        });

    }


    private void createAccount(final String email, String password) {

        if (!validateFormSignUp()) {

            return;
        } else {

            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                finish();
                                Intent i = new Intent(AuthActivity.this, Splash.class);
                                startActivity(i);


                                // Write a message to the database
                                database = FirebaseDatabase.getInstance();
                                myRef = database.getReference("users");


                                String key = mAuth.getCurrentUser().getUid();
                                userInformation userInfor = new userInformation(getname, key, email);

                                myRef.child(key).setValue(userInfor);


                            } else {

                                // If sign in fails, display a message to the user.
                                Toast.makeText(AuthActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
            // [END create_user_with_email]
        }
    }

    //
    private boolean validateFormSignUp() {
        boolean valid = true;

          getEmail = email.getText().toString().trim();
          getPassword = password.getText().toString().trim();
        getname = name.getText().toString().trim();


        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Email Required.");
            valid = false;
        }

        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Password  Required");
        }

        if (TextUtils.isEmpty(getname)) {
            name.setError(" name  Required");
        }


        return valid;
    }


    private boolean validateSignIn() {
        boolean valid = true;

        String getEmail    = email.getText().toString().trim();
        String   getPassword = password.getText().toString().trim();


        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Email Required.");
            valid = false;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Password  Required");
        }



        return valid;
    }

    //Now start sign in process

    public void callSignIn(String email, String password) {


        if (!validateSignIn()) {

            return;
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        public static final String TAG = "Auth";

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(AuthActivity.this, "email or password do not match", Toast.LENGTH_SHORT).show();
                            } else {

                                finish();
                                startActivity(new Intent(getApplicationContext(), Splash.class));


                            }

                        }
                    });
        }
    }
    public void tvLogin(View view) {
        Intent i = new Intent(AuthActivity.this, Auth_loginActivity.class);
        startActivity(i);
    }





}
