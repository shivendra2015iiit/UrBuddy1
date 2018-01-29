package com.shivendra.hp.urbuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class processActivity extends AppCompatActivity {

    String position;
    String day, sday;
    private DatabaseReference mRootRef;
    private DatabaseReference Repeatref;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    Date currentTime,ctomorrow;
    Date f,t;
    String wday,today,tommorrow,wtday;
    String time;
    int daynumber;
    static String[] user;
    NumberPicker sq;
    NumberPicker tq;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        Bundle bundle = getIntent().getExtras();
        position = bundle.getString("P");

        sq = (NumberPicker) findViewById(R.id.snacksquantity);
        tq = (NumberPicker) findViewById(R.id.teaquantity);

        sq.setMinValue(0);
        sq.setMaxValue(15);

        tq.setMinValue(0);
        tq.setMaxValue(10);

        switch (position) {
            case "0":
                day = "Monday";
                sday = "Mon";
                break;
            case "1":
                day = "Tuesday";
                sday = "Tue";
                break;
            case "2":
                day = "Wednesday";
                sday = "Wed";
                break;
            case "3":
                day = "Thursday";
                sday = "Thu";
                break;
            case "4":
                day = "Friday";
                sday = "Fri";
                break;
            case "5":
                day = "Saturday";
                sday = "Sat";
                break;
            case "6":
                day = "Sunday";
                sday = "Sun";
                break;
            default:
        }


        // internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        while (activeNetwork == null) {
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
        //


        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        user = currentUser.getEmail().split("@");


        ab = getSupportActionBar();
        ab.setTitle("Menu ");

        Toast.makeText(getApplicationContext(), "Quantity SET TO 0 ", Toast.LENGTH_SHORT).show();
        final DatabaseReference smenuroot = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/SnackMenu/" + sday);

        mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/nUser/" + user[0] + "/" + day);

        Repeatref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/nUser/" + user[0]);


        smenuroot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ab.setSubtitle(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRootRef.child("SQ").setValue(0);
        mRootRef.child("TQ").setValue(0);


        // day processing

        currentTime = Calendar.getInstance().getTime();
        // SimpleDateFormat formatter_from = new SimpleDateFormat("EEE MMM dd hh:mm:ss ZZ yyyy", Locale.ENGLISH);
        SimpleDateFormat format_day = new SimpleDateFormat("EEE",Locale.ENGLISH);
        SimpleDateFormat formatter_time = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
        SimpleDateFormat tdate = new SimpleDateFormat("dd/MMM/yyyy",Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        ctomorrow = calendar.getTime();

        try {
            today=tdate.format(currentTime);
            tommorrow=tdate.format(ctomorrow);
            wtday = format_day.format(ctomorrow);
            wday = format_day.format(currentTime);
            time = formatter_time.format(currentTime);
            t = formatter_time.parse(time);
            f = formatter_time.parse("12:59");




        Repeatref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Repeat").getValue().equals("-1")) {
                    sq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                            mRootRef.child("SQ").setValue(newVal + "");
                        }
                    });

                    tq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                            mRootRef.child("TQ").setValue(newVal + "");
                        }
                    });
                } else {
                    if (f.after(t)) {
                        if (!sday.equals(wday)) {
                                     ////////////////////////////////////
                            AlertDialog.Builder ad = new AlertDialog.Builder(processActivity.this);
                            ad.setMessage("IN NON REPEAT MODE YOU CANNOT BOOK FOR OTHERDAYS.Go and Book for "+wday+ "day only");
                            ad.setTitle("Wrong day");
                            ad.setCancelable(false);
                            ad.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(processActivity.this, Booksnacks.class);
                                    startActivity(i);
                                    processActivity.this.finish();
                                }
                            });
                            ad.show();
                        }else{
                            Repeatref.child("Repeat").setValue(today);
                            Snackbar.make(getCurrentFocus(), "Your order would be taken for "+today, Snackbar.LENGTH_LONG).show();
                            sq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                                @Override
                                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                                    mRootRef.child("SQ").setValue(newVal + "");
                                }
                            });

                            tq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                                @Override
                                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                                    mRootRef.child("TQ").setValue(newVal + "");
                                }
                            });

                        }
                    }else{
                        daynumber = getday(wday);
                        daynumber=daynumber+1;
                        daynumber = daynumber%7;
                        String wday1=getdayname(daynumber);
                        if (!sday.equals(wday1)) {
                             AlertDialog.Builder ad = new AlertDialog.Builder(processActivity.this);
                            ad.setMessage("IN NON REPEAT MODE YOU CANNOT BOOK FOR OTHERDAYS.Go and Book for "+wday1+ "day only");
                            ad.setTitle("Wrong day");
                            ad.setCancelable(false);
                            ad.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(processActivity.this, Booksnacks.class);
                                    startActivity(i);
                                    processActivity.this.finish();
                                }
                            });
                            ad.show();
                        }else{
                            Repeatref.child("Repeat").setValue(tommorrow);
                            Snackbar.make(getCurrentFocus(), "Your order would be taken for "+tommorrow, Snackbar.LENGTH_LONG).show();
                            sq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                                @Override
                                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                                    mRootRef.child("SQ").setValue(newVal + "");
                                }
                            });

                            tq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                                @Override
                                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                                    mRootRef.child("TQ").setValue(newVal + "");
                                }
                            });

                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(processActivity.this, Booksnacks.class);
        startActivity(i);
        processActivity.this.finish();
    }

    public int getday(String day) {
        switch (day) {
            case "Mon":
                return 0;
            case "Tue":
                return 1;
            case "Wed":
                return 2;
            case "Thu":
                return 3;
            case "Fri":
                return 4;
            case "Sat":
                return 5;
            case "Sun":
                return 6;

            default:
                return -1;
        }
    }
    public String getdayname(int day){
        switch (day) {
            case 0: return "Mon";
            case 1: return "Tue";
            case 2: return "Wed";
            case 3: return "Thu";
            case 4: return "Fri";
            case 5: return "Sat";
            case 6: return "Sun";

            default: return null;
        }
    }
}
