package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Auth_login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button signIn, signUp;

    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);
        signIn = (Button) findViewById(R.id.btn_sign_in);
        signUp = (Button) findViewById(R.id.btn_sign_up);


        //Checks if user is already logged in
        if (mAuth.getCurrentUser() != null) {


//            User not logged in
            finish();
            startActivity(new Intent(getApplicationContext(), Splash.class));
        }

        signIn.setOnClickListener(new View.OnClickListener() {
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
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Auth_login.this, AuthActivity.class);
                startActivity(i);

            }
        });


    }


    private boolean validateForm() {
        boolean valid = true;

        String getEmail = email.getText().toString().trim();
        String getPassword = password.getText().toString().trim();


        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Email Required.");
            valid = false;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Password  Required");
        }

        return valid;
    }

    private boolean validateSign() {
        boolean valid = true;

        String getEmail = email.getText().toString().trim();
        String getPassword = password.getText().toString().trim();


        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Email Required.");
            valid = false;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Password  Required");
        }


        return valid;
    }

    public void callSignIn(String email, String password) {

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
                            Toast.makeText(Auth_login.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), Splash.class));
                        }

                    }
                });
    }
}
