package com.shivendra.hp.urbuddy.commentData;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shivendra HP on 11-02-2018.
 */

public class commentCollection {

    public final static ArrayList<comment> comments = new ArrayList<>();
    public static ArrayList<comment> getcollection(DataSnapshot ds) {
        comments.clear();
        comment m;

        for (DataSnapshot d : ds.getChildren()){
            m = new comment();
            Log.w("array",d.getKey()+"   "+d.getValue());
            try {
                m.setComment(d.child("value").getValue().toString());
                m.setDate(getdatefrommills(d.getKey()));
                comments.add(m);
            }catch (Exception e){

            }
        }
        return comments;
    }

    public static String getdatefrommills(String timeStamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeStamp));

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int time = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        return  " "+time+":"+min+" Hrs  on "+mDay+"/"+mMonth+"/"+mYear;
    }

}
