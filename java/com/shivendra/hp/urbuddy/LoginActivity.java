package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser!=null){
              if(currentUser.isEmailVerified()) {
                  Intent I = new Intent(getApplicationContext(), drawer.class);  //launch the main drawer activity
                  startActivity(I);
                  LoginActivity.this.finish();
              }
              else{
                  Toast.makeText(getApplicationContext(), R.string.verifyemail, Toast.LENGTH_LONG).show();
              }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Initializing firebase auth object
        firebaseAuth =FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        //initializing login button
        login = (Button)findViewById(R.id.login);
        //setting OnClickListner to login
        login.setOnClickListener(onClickListener);
    }

    // function to launch signulp activity

       public void launchsignup(View v){
           progressDialog.setMessage("Please wait");
           progressDialog.show();
           progressDialog.setCancelable(false);
           progressDialog.setCanceledOnTouchOutside(false);
          Intent I = new Intent(LoginActivity.this,signup.class);
          startActivity(I);
          LoginActivity.this.finish();
       }

       //function to reset email

    public void sendpassresetlink(View v){
        String E = email.getText().toString().trim();
        if(TextUtils.isEmpty(E) || !android.util.Patterns.EMAIL_ADDRESS.matcher(E).matches()){
            Snackbar.make(v, R.string.validid, Snackbar.LENGTH_LONG).show();
            return;
        }
        if(!checkdomain( E)){
            Snackbar.make(v, R.string.no_valid_domain, Snackbar.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.sendPasswordResetEmail(E);
        Snackbar.make(v, R.string.resetLink, Snackbar.LENGTH_LONG).show();
    }



    private OnClickListener onClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
        String pass = password.getText().toString().trim();
        String E = email.getText().toString().trim();


        //checking if username and password are not empty
        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(E)) {
            Snackbar.make(v, R.string.fillall, Snackbar.LENGTH_SHORT).show();
            return;
        }

        // checking for invalid email-id formate
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(E).matches()) {
            Toast t = Toast.makeText(getApplicationContext(), R.string.validid, Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        // check the domain address  AT THIS TIME ONLY @IIITKOTTAYAM.AC.IN WILL BE ALLOWED(Usage 1/2 Another usage signup class)
        // WE CAN GIVE UPDATE TO APP BY ADDING MORE DOMAINS IN SELECTOR

        if(!checkdomain( E)){
            Snackbar.make(v, R.string.no_valid_domain, Snackbar.LENGTH_LONG).show();
            return;
        }
        // check if password is 7 digits
        if (pass.length() < 7) {
            Snackbar.make(v, R.string.passlen, Snackbar.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Logging In");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            // everthing is syntaxtically allright
            firebaseAuth.signInWithEmailAndPassword(E,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent I = new Intent(getApplicationContext(),drawer.class) ;  //launch the main activity
                        startActivity(I);
                        LoginActivity.this.finish();
                    }
                    else{
                        progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,
                                    R.string.nologin
                                    , Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }  else {
            // not connected to the internet
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
            // notify user you are not online

        }


    }

    };
// function to varify domains
    public boolean checkdomain(String email) {
        if (email.contains("@iiitkottayam.ac.in")) {
            return true;
        }
            return false;
    }

   }








