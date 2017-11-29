package com.example.khutsomatlala.hackaton_user11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {
    EditText enterEmail;
    Button confirmPassword;
    FirebaseAuth auth;
    LinearLayout activity_forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        enterEmail = (EditText) findViewById(R.id.edt_email);
        activity_forgot = (LinearLayout) findViewById(R.id.activity_forgot) ;

        auth = FirebaseAuth.getInstance();


    }

    public void BtnConfirm(View view){
        auth.sendPasswordResetEmail(enterEmail.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Forgot_Password.this, "We have sent password to email: "+ enterEmail.getText().toString(), Toast.LENGTH_SHORT).show();
//                            Snackbar snackBar = Snackbar.make(activity_forgot,"We have sent password to email: "+ enterEmail.getText().toString(),Snackbar.LENGTH_SHORT);
//                            snackBar.show();
                            Intent i = new Intent(Forgot_Password.this,Auth_login.class);
                            startActivity(i);
                        }
                        else {
                            Snackbar snackBar = Snackbar.make(activity_forgot,"Failed to send password",Snackbar.LENGTH_SHORT);
                            snackBar.show();

                        }

                    }
                });
    }
}
