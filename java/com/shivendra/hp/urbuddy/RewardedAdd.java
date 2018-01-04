package com.shivendra.hp.urbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardedAdd extends AppCompatActivity implements RewardedVideoAdListener {
    private TextView coinsearned;
    private RewardedVideoAd mRewardedVideoAd;
    private DatabaseReference mRewardRef;
    private FirebaseAuth firebaseAuth;

    private int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_add);

        firebaseAuth = FirebaseAuth.getInstance();
        mRewardRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/Rewards");
        coinsearned = (TextView) findViewById(R.id.coin_textview);


        mRewardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    coinsearned.setText("Your Coins : "+dataSnapshot.child(firebaseAuth.getUid()+"").getValue().toString());
                }catch (Exception e){
                    coinsearned.setText("Your Coins : 0 ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        MobileAds.initialize(this,"ca-app-pub-9873932497544871~3261525182");
       /* MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
       */ mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();





    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-9873932497544871/2767481418",
                new AdRequest.Builder().build());
    }
    public void playvideo(View v){
        i=0;
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();

        }
        else{
             Toast.makeText(this,"Patience Your video is Loading ",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
         if(i==0) {
             mRewardedVideoAd.show();
             i=1;
         }

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
              loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(final RewardItem reward) {
        mRewardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(DataSnapshot dataSnapshot) {
                                                 int current_coin=0;
                                                 try {
                                                   current_coin = Integer.parseInt(dataSnapshot.child(firebaseAuth.getUid()).getValue().toString());
                                                 }catch (Exception e){

                                                 }
                                                 mRewardRef.child(firebaseAuth.getUid()).setValue(current_coin+reward.getAmount());
                                             }

                                             @Override
                                             public void onCancelled(DatabaseError databaseError) {
                                                 Toast.makeText(getApplicationContext(),"Database error Reward "+databaseError,Toast.LENGTH_SHORT).show();
                                             }
                                         });
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(this,"Failed to load",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RewardedAdd.this,drawer.class);
        startActivity(i);
        RewardedAdd.this.finish();
    }
}
