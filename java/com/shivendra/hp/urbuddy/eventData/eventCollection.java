package com.shivendra.hp.urbuddy.eventData;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.shivendra.hp.urbuddy.homeData.panel;

import java.util.ArrayList;

/**
 * Created by Shivendra HP on 09-02-2018.
 */

public class eventCollection {
    public final static ArrayList<event> events = new ArrayList<>();
    public static ArrayList<event> getpanelcollection(DataSnapshot ds) {
        events.clear();
        event m = new event();


        for (DataSnapshot d : ds.getChildren()){

            m = new event();
            try{
                m.setHeading(d.child("Heading").getValue().toString());
            }catch (Exception e){
                Log.w("Tag : ","No Heading part in event card");
            }
            try {
                m.setTextevent(d.child("Text").getValue().toString());
            }catch (Exception e){
                Log.w("Tag : ","No text part in event card");
            }
            try{
                m.setImageurl(d.child("Image").getValue().toString());
            }catch (Exception e) {
                Log.w("Tag : ","No image part in event card");
            }
            events.add(m);

        }

        return  events;
    }
}
