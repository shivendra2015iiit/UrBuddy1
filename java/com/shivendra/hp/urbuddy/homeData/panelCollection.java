package com.shivendra.hp.urbuddy.homeData;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Shivendra HP on 01-01-2018.
 */

public class panelCollection {
    public final static ArrayList<panel> panels = new ArrayList<>();
    public static ArrayList<panel> getpanelcollection(DataSnapshot ds) {

        panel m = new panel();

        for (DataSnapshot d : ds.getChildren()){

                m = new panel();
            try{
                m.setHeading(d.child("Heading").getValue().toString());
            }catch (Exception e){
                Log.w("Tag : ","No Heading part in home card");
            }
                try {
                    m.setTexthome(d.child("Text").getValue().toString());
                }catch (Exception e){
                    Log.w("Tag : ","No text part in home card");
                }
                try{
                m.setImageurl(d.child("Image").getValue().toString());
                panels.add(m);
            }catch (Exception e) {
                    Log.w("Tag : ","No image part in home card");
                }

        }

        return  panels;
    }
}
