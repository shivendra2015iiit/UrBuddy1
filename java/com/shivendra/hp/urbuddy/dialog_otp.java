package com.shivendra.hp.urbuddy;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;;
import android.widget.ProgressBar;
import android.widget.TextView;

public class dialog_otp extends AppCompatActivity {
    ProgressBar progressBar;
    TextView resend;

    MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_otp);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        resend = (TextView)findViewById(R.id.resend);

        progressBar.setProgress(100);
        myCountDownTimer = new MyCountDownTimer(10000, 500);
        myCountDownTimer.start();

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
