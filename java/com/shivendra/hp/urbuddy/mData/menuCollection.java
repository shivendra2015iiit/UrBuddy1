package com.shivendra.hp.urbuddy.mData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.drm.DrmManagerClient;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shivendra HP on 19-12-2017.
 */

public class menuCollection {

    public static ArrayList<menu> getmenucollection(DataSnapshot ds){

        menu m = new menu();



        final ArrayList<menu> menus = new ArrayList<>();



             m.setDayname("Monday");
             m.setSnacksimage("");
             m.setSnackscount("Snacks : " + ds.child("Monday").child("SQ").getValue());
             m.setTeacount("Beverages : " + ds.child("Monday").child("TQ").getValue());
             menus.add(m);

            m = new menu();
            m.setDayname("Tuesday");
            m.setSnacksimage("");
            m.setSnackscount("Snacks : " + ds.child("Tuesday").child("SQ").getValue());
            m.setTeacount("Beverages : " + ds.child("Tuesday").child("TQ").getValue());
            menus.add(m);

            m = new menu();
            m.setDayname("Wednesday");
            m.setSnacksimage("");
            m.setSnackscount("Snacks : " + ds.child("Wednesday").child("SQ").getValue());
            m.setTeacount("Beverages : " + ds.child("Wednesday").child("TQ").getValue());
            menus.add(m);

            m = new menu();
            m.setDayname("Thursday");
            m.setSnacksimage("");
            m.setSnackscount("Snacks : " + ds.child("Thursday").child("SQ").getValue());
            m.setTeacount("Beverages : " + ds.child("Thursday").child("TQ").getValue());
            menus.add(m);

            m = new menu();
            m.setDayname("Friday");
            m.setSnacksimage("");
            m.setSnackscount("Snacks : " + ds.child("Friday").child("SQ").getValue());
            m.setTeacount("Beverages : " + ds.child("Friday").child("TQ").getValue());
            menus.add(m);

            m = new menu();
            m.setDayname("Saturday");
            m.setSnacksimage("");
            m.setSnackscount("Snacks : " + ds.child("Saturday").child("SQ").getValue());
            m.setTeacount("Beverages : " + ds.child("Saturday").child("TQ").getValue());
            menus.add(m);

            m = new menu();
            m.setDayname("Sunday");
            m.setSnacksimage("");
            m.setSnackscount("Snacks : " + ds.child("Sunday").child("SQ").getValue());
            m.setTeacount("Beverages : " + ds.child("Sunday").child("TQ").getValue());
            menus.add(m);

        return menus;
    }



}
