package com.example.khutsomatlala.hackaton_user11;

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

import com.example.khutsomatlala.hackaton_user11.model.userInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthActivity extends Activity {

    private FirebaseAuth mAuth;

    String  getname,getCell,getSurname   ;

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

                String getEmail = email.getText().toString().trim();
                String getPassword = password.getText().toString().trim();

                createAccount(getEmail, getPassword);
                callSignIn(getEmail, getPassword);

            }
        });

    }


    private void createAccount(String email, String password) {

        if (!validateForm()) {


            return;
        } else {

            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information


                                Intent i = new Intent(AuthActivity.this,Splash.class);
                                startActivity(i);


                                // Write a message to the database
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users");

                                userInformation userInfor = new userInformation(getname, getSurname, getCell);

                                String key = myRef.push().getKey();
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
    private boolean validateForm() {
        boolean valid = true;

       String getEmail    = email.getText().toString().trim();
        String   getPassword = password.getText().toString().trim();
          getname = name.getText().toString().trim();
          getSurname = surname.getText().toString().trim();
           getCell = cell.getText().toString().trim();


        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Email Required.");
            valid = false;
        }
          if (TextUtils.isEmpty(getPassword)) {
            password.setError("Password  Required");
        }

        if (TextUtils.isEmpty(getname)) {
            name.setError(" Name Required.");
            valid = false;
        }

        if (TextUtils.isEmpty(getSurname)) {
            surname.setError(" Surname Required.");
            valid = false;
        }

        if (TextUtils.isEmpty(getCell)) {
            cell.setError(" Cell Required.");
            valid = false;
        }


        return valid;
    }

    //
    private boolean validateSign() {
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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public static final String TAG ="Auth" ;

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                         Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(AuthActivity.this, "auth_failed", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), Splash.class));
                        }

                    }
                });
    }

    public void tvLogin(View view){
        Intent i = new Intent(AuthActivity.this,Auth_login.class);
        startActivity(i);
    }
}
