package com.shivendra.hp.urbuddy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
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

public class processActivity extends AppCompatActivity {

    String position;
    String day,sday;
    private  DatabaseReference mRootRef;
    private  FirebaseAuth firebaseAuth;
    private  FirebaseUser currentUser;
    static String[] user;
    NumberPicker sq;
    NumberPicker tq;
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        Bundle bundle = getIntent().getExtras();
        position =bundle.getString("P");

        sq = (NumberPicker) findViewById(R.id.snacksquantity);
        tq = (NumberPicker) findViewById(R.id.teaquantity);

        sq.setMinValue(0);
        sq.setMaxValue(15);

        tq.setMinValue(0);
        tq.setMaxValue(10);

        switch (position) {
            case "0": day = "Monday";  sday="Mon";  break;
            case "1": day = "Tuesday"; sday="Tue";   break;
            case "2": day = "Wednesday"; sday="Wed"; break;
            case "3": day = "Thursday"; sday="Thu"; break;
            case "4": day = "Friday";   sday="Fri"; break;
            case "5": day = "Saturday"; sday="Sat"; break;
            case "6": day = "Sunday";   sday="Sun"; break;
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
        final DatabaseReference smenuroot = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/SnackMenu/"+sday);

        mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/nUser/"+user[0]+"/"+day);


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

        sq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                mRootRef.child("SQ").setValue(newVal+"");
            }
            });

        tq.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                mRootRef.child("TQ").setValue(newVal+"");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(processActivity.this,Booksnacks.class);
        startActivity(i);
        processActivity.this.finish();
    }
}
