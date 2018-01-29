package com.shivendra.hp.urbuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivendra.hp.urbuddy.mData.menuCollection;
import com.shivendra.hp.urbuddy.mRecycler.RecyclerItemClickListener;
import com.shivendra.hp.urbuddy.mRecycler.myAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Booksnacks extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView rv;
    myAdapter adapter;
    private DatabaseReference mRootRef;
    private  FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private String[] user;
    Date currentTime,ctomorrow;
    Date f,t;
    String wday,today,tommorrow,wtday;
    String time;
    int daynumber;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booksnacks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));


        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle("Snacks Menu");



//firebase
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        user = currentUser.getEmail().split("@");

        mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/nUser/"+user[0]);

 //firebase


        if (!isTimeAutomatic(getApplicationContext())){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Booksnacks.this);
            dialog.setTitle("TIME IS MANUAL");
            dialog.setMessage("Please Go to settings > Date and Time > Set Time To Automatic and restart this app");
            dialog.setCancelable(false);
            dialog.show();
        }else {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork == null) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Booksnacks.this);
                dialog.setTitle("No Active Internet Connection");
                dialog.setMessage("Please connect to internet to continue");
                dialog.setCancelable(true);
                dialog.show();

            }else{
            pd= new ProgressDialog(Booksnacks.this);
            pd.setMessage("Loading ...");
            pd.setCancelable(true);
            pd.show();
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
              //  d = formatter_from.parse(date);

                 today=tdate.format(currentTime);
                tommorrow=tdate.format(ctomorrow);
                wtday = format_day.format(ctomorrow);
                 wday = format_day.format(currentTime);
                time = formatter_time.format(currentTime);
                t = formatter_time.parse(time);
                f = formatter_time.parse("12:59");
                daynumber = getday(wday);

            //


            mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    adapter = new myAdapter(getApplicationContext(), menuCollection.getmenucollection(dataSnapshot));
                    rv.setAdapter(adapter);
                    pd.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Database error2: " +databaseError,Toast.LENGTH_SHORT).show();
                }
            });


            rv.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent i = new Intent(Booksnacks.this, processActivity.class);
                    Bundle b = new Bundle();
                    b.putString("P", position + "");
                    i.putExtras(b);
                    startActivity(i);
                    Booksnacks.this.finish();
                }
            }));
            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (f.after(t)) {
                            if (dataSnapshot.child("Repeat").getValue() == null) {
                                mRootRef.child("Repeat").setValue(today);
                                Toast.makeText(getApplicationContext(), "Your Snacks is Noted for only "+ today+" "+wday, Toast.LENGTH_LONG).show();
                            }
                            else{
                                mRootRef.child("Repeat").setValue(tommorrow);
                                Toast.makeText(getApplicationContext(), "Your Snacks is Noted for only "+ tommorrow+" "+wtday, Toast.LENGTH_LONG).show();

                            }
                                fab.setImageResource(R.drawable.norepeat);

                            } else if (dataSnapshot.child("Repeat").getValue().equals("-1")) {


                                if (f.after(t)) {
                                    mRootRef.child("Repeat").setValue(today);
                                    Toast.makeText(getApplicationContext(), "Your Snacks is Noted for only "+ today+" "+wday, Toast.LENGTH_LONG).show();

                                }
                                else{
                                    mRootRef.child("Repeat").setValue(tommorrow);
                                    Toast.makeText(getApplicationContext(), "Your Snacks is Noted for only "+ tommorrow+" "+wtday, Toast.LENGTH_LONG).show();

                                }

                                
                                    Snackbar.make(view, "Your Menu will NOT Be Auto-Booked", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                                fab.setImageResource(R.drawable.norepeat);

                            } else {
                                mRootRef.child("Repeat").setValue("-1");
                                Snackbar.make(view, "Your Menu Will Be Auto-Booked", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                                fab.setImageResource(R.drawable.repeat);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                                   Toast.makeText(getApplicationContext(),"Database error1: " +databaseError,Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.child("Repeat").getValue() == null) {
                        if (f.after(t)) {
                        mRootRef.child("Repeat").setValue(today);
                            Toast.makeText(getApplicationContext(), "Your Snacks is Noted for only "+ today+" "+wday, Toast.LENGTH_LONG).show();
                        }
                        else{
                            mRootRef.child("Repeat").setValue(tommorrow);
                            Toast.makeText(getApplicationContext(), "Your Snacks is Noted for only "+ tommorrow+" "+wtday, Toast.LENGTH_LONG).show();

                        }

                        fab.setImageResource(R.drawable.norepeat);
                        }
                       else if (dataSnapshot.child("Repeat").getValue().equals("-1")) {
                        fab.setImageResource(R.drawable.repeat);
                    } else {
                        fab.setImageResource(R.drawable.norepeat);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"You can't view this section with guest ID : " +databaseError,Toast.LENGTH_SHORT).show();
                }

            });
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        }


    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Booksnacks.this, drawer.class);
        startActivity(i);
        Booksnacks.this.finish();

    }
    public static boolean isTimeAutomatic(Context c) {
         return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;

    }

    public int getday(String day){
        switch (day) {
            case "Mon": return 0;
            case "Tue": return 1;
            case "Wed": return 2;
            case "Thu": return 3;
            case "Fri": return 4;
            case "Sat": return 5;
            case "Sun": return 6;

            default: return -1;
        }
    }
}
