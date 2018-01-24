package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    ProgressDialog progressDialog;
    ProgressDialog pg;
    private FirebaseAuth firebaseAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            if (currentUser.isEmailVerified()) {

                /////////////////////////////////////////////

                Intent I = new Intent(getApplicationContext(), drawer.class);  //launch the main drawer activity
                startActivity(I);
                LoginActivity.this.finish();
            } else {
                ProgressDialog.Builder pd = new ProgressDialog.Builder(this);
                pd.setMessage(R.string.verifyemail);
                pd.setCancelable(false);
                pd.setPositiveButton("Resend mail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.getCurrentUser().sendEmailVerification();

                    }
                });
                pd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                pd.show();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        pg = new ProgressDialog(this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        //initializing login button
        login = (Button) findViewById(R.id.login);
        //setting OnClickListner to login
        login.setOnClickListener(onClickListener);
    }

    // function to launch signulp activity

    public void launchsignup(View v) {
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        Intent I = new Intent(LoginActivity.this, signup.class);
        startActivity(I);
        LoginActivity.this.finish();
    }

    //function to reset email

    public void sendpassresetlink(View v) {
        String E = email.getText().toString().trim();
        if (TextUtils.isEmpty(E) || !android.util.Patterns.EMAIL_ADDRESS.matcher(E).matches()) {
            Snackbar.make(v, R.string.validid, Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!checkdomain(E)) {
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

            if (!checkdomain(E)) {
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

                ///////////////////////////////
                // connected to the internet
                // everthing is syntaxtically allright
                firebaseAuth.signInWithEmailAndPassword(E, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                progressDialog.dismiss();
                                Intent I = new Intent(getApplicationContext(), drawer.class);  //launch the main activity
                                startActivity(I);
                                LoginActivity.this.finish();
                            } else {
                                progressDialog.dismiss();
                                ProgressDialog.Builder pd = new ProgressDialog.Builder(LoginActivity.this);
                                pd.setMessage(R.string.verifyemail);
                                pd.setCancelable(false);
                                pd.setPositiveButton("Resend mail", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        firebaseAuth.getCurrentUser().sendEmailVerification();

                                    }
                                });
                                pd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                pd.show();

                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,
                                    R.string.nologin
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
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

    // fuction to login as guest

    public void loginguest(View view) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final ProgressDialog p1 = new ProgressDialog(this);
        p1.setMessage("Logging in as guest...");
        p1.setCanceledOnTouchOutside(false);
        p1.setCancelable(false);
        p1.show();
        if (activeNetwork != null) {
            firebaseAuth.signInWithEmailAndPassword("guest@iiitkottayam.ac.in", "guest1234").addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                            p1.dismiss();
                            Intent I = new Intent(getApplicationContext(), drawer.class);  //launch the main activity
                            startActivity(I);
                            LoginActivity.this.finish();
                        } else {
                            p1.dismiss();
                            final ProgressDialog.Builder pd = new ProgressDialog.Builder(LoginActivity.this);
                            pd.setMessage(R.string.verifyemail);
                            pd.setCancelable(true);
                            pd.setPositiveButton("Resend mail", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification();

                                }

                            });
                            pd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            pd.show();

                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,
                                R.string.nologin
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // not connected to the internet
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
            // notify user you are not online

        }


    }
}








