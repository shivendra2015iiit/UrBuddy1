package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText email;
    EditText password;
    EditText confpassword;
    Button Singup;
    TextView signin;
    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


//setting status bar color

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorBlue));

        firebaseAuth =FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confpassword = (EditText)findViewById(R.id.confpassword);
        Singup =(Button)findViewById(R.id.Signup);
        signin = (TextView)findViewById(R.id.signin);
        progressDialog = new ProgressDialog(this);
    }
    public void submitclicked(final View v) {
        String cpass = confpassword.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String Email = email.getText().toString().trim();

        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(cpass) || TextUtils.isEmpty(Email)) {
            Snackbar.make(v, R.string.fillall, Snackbar.LENGTH_SHORT).show();
            return;
        }
        // checking for invalid email-id formate
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            Toast t = Toast.makeText(getApplicationContext(), R.string.validid, Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        // check the domain address  AT THIS TIME ONLY @IIITKOTTAYAM.AC.IN WILL BE ALLOWED(Usage 2/2)
        // WE CAN GIVE UPDATE TO APP BY ADDING MORE DOMAINS IN SELECTOR

        if (!checkdomain(Email)) {
            Snackbar.make(v, R.string.no_valid_domain, Snackbar.LENGTH_LONG).show();
            return;
        }
        // check if password is 7 digits
        if (pass.length() < 7) {
            Snackbar.make(v, R.string.passlen, Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(cpass)) {
            Snackbar.make(v, R.string.confAgain, Snackbar.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Signing Up");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            firebaseAuth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()) {
                        Snackbar.make(v, R.string.success , Snackbar.LENGTH_SHORT).show();
                         firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()) {
                                     Intent I = new Intent(signup.this,LoginActivity.class) ;  //launch the main activity
                                     startActivity(I);
                                     signup.this.finish();
                                 }
                                 else{
                                     Snackbar.make(v,"Email Doesn't exit contact Admin" , Snackbar.LENGTH_SHORT).show();
                                 }
                             }
                         });;

                    }
                }
            });
        }
    }
    // function to varify domains
    public boolean checkdomain(String email) {
        if (email.contains("@iiitkottayam.ac.in")) {
            return true;
        }
        return false;
    }

    // function to launch signin activity

    public void launchsignin(View v){
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        Intent I = new Intent(signup.this,LoginActivity.class);

        startActivity(I);
        signup.this.finish();
    }
}