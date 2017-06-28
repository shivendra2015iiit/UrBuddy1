package com.shivendra.hp.urbuddy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText email;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        //initializing login button
        login = (Button)findViewById(R.id.button);
        //setting OnClickListner to login
        login.setOnClickListener(onClickListener);
    }

    public void insertuser(View v) {
        username.setText("");
    }
    public void insertemail(View v) {
         email.setText("");
  }

    private OnClickListener onClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
        String User = username.getText().toString().trim();
        String E = email.getText().toString().trim();
        //*1

        Intent intent = new Intent(LoginActivity.this,
                dialog_otp.class);
        startActivity(intent);



        ///////////////

        //checking if username and password are not empty
        if(TextUtils.isEmpty(User)&&TextUtils.isEmpty(E)) {
            Toast t = Toast.makeText(getApplicationContext(),"Please Fill all fields",Toast.LENGTH_SHORT);
            t.show();

        }
        else {
             // checking for invalid email-id formate
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(E).matches()) {
                Toast t = Toast.makeText(getApplicationContext(), "Please Enter valid Email", Toast.LENGTH_SHORT);
                t.show();
            }
            else{
                // everthing is syntaxtically allright
                ///////////////////////////////////////*1 paste back here

                //////////////////////////////////
            }
        }
    }
    };
}

