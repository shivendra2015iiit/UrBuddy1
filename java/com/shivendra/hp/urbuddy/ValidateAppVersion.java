package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.ads.InterstitialAd;

public class ValidateAppVersion extends AppCompatActivity {

    private DatabaseReference appsuportRef;
    private  ProgressDialog pd;
    private AlertDialog.Builder ad;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_app_version);


        //
        MobileAds.initialize(this,"ca-app-pub-9873932497544871~3261525182");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9873932497544871/2582938142");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {

                // Code to be executed when when the interstitial ad is closed.
            }
        });

        //advertisement
        appsuportRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/appsuport");
        pd = new ProgressDialog(this);
        ad = new AlertDialog.Builder(this);
        pd.setCancelable(false);
        pd.setMessage("Checking App Integrity...");
        pd.show();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            pd.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(ValidateAppVersion.this);
            dialog.setTitle("No Active Internet Connection");
            dialog.setMessage("Please connect to internet to continue");
            dialog.setCancelable(true);
            dialog.show();

        } else {
            appsuportRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pd.dismiss();
                    try {
                        if (dataSnapshot.child("216").getValue().toString().equals("1")) {

                            Intent i = new Intent(ValidateAppVersion.this, LoginActivity.class);
                            startActivity(i);
                            ValidateAppVersion.this.finish();
                        } else if (dataSnapshot.child("216").getValue().toString().equals("0")) {
                            ad.setCancelable(false);
                            ad.setTitle("GOOD NEWS !");
                            ad.setMessage("A New Version Of App Is Available. This Version is no longer supported by developer.Contact Beta Team");
                            ad.setPositiveButton("GOT IT ! ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent viewIntent =
                                            new Intent("android.intent.action.VIEW",
                                                    Uri.parse("https://drive.google.com/open?id=1UTx5XuIPV7VcGP-LL97eP8-JSXm_Ujzm"));
                                    startActivity(viewIntent);
                                    ValidateAppVersion.this.finish();
                                    finish();
                                }
                            });
                            ad.show();
                        }
                    } catch (Exception e) {
                        Log.e("NUll POInt APPSUPORT : ", e + "");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    pd.dismiss();
                }
            });
        }
    }
}
