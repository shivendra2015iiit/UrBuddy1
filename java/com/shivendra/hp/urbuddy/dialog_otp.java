package com.shivendra.hp.urbuddy;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class dialog_otp extends AppCompatActivity {
    ProgressBar progressBar;
    TextView resend;
    EditText getotp;
    private FirebaseAuth firebaseAuth;

    MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_otp);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        resend = (TextView)findViewById(R.id.resend);
        getotp = (EditText) findViewById(R.id.getotp) ;
        firebaseAuth =FirebaseAuth.getInstance();

        progressBar.setProgress(100);
        myCountDownTimer = new MyCountDownTimer(10000, 500);
        myCountDownTimer.start();

    }
    public void submitclicked(View view){
         String otp=getotp.getText().toString().trim();    //getting otp from user
        if(TextUtils.isEmpty(otp)){
            Toast.makeText(this,"Please Enter OTP first",Toast.LENGTH_SHORT);
            return;
        }
        String E=getIntent().getStringExtra("email_id");
        firebaseAuth.signInWithEmailAndPassword(E,otp).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                        Intent I = new Intent(getApplicationContext(),MainActivity.class) ;  //launch the main activity
                     dialog_otp.this.finish();
                }
            }
        });
    }

       //setting up of clock function (timer progress bar)
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished / 100);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            resend.setText(getResources().getString(R.string.resendotp));         // resend OTP message
            resend.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }
}
