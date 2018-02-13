package com.shivendra.hp.urbuddy.suggestionData;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.shivendra.hp.urbuddy.galleryData.photo;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by Shivendra HP on 03-01-2018.
 */

public class thoughtCollection {
    public static ArrayList<thought> thoughts =new ArrayList<thought>();
    public static ArrayList<thought> getthoughtcollection(DataSnapshot ds,String uid){

        thoughts.clear();
        thought m ;


        for (DataSnapshot d : ds.getChildren()){
            try {
                m = new thought();
                float positive=0;
                float negative = 0;
                float percent = 0;
                int color = 0;
                m.setTimestamp(d.getKey());
                m.setThought(d.child("Thought").getValue().toString());
                try {
                    positive=(float) d.child("Positive").getChildrenCount();
                }catch (Exception e){

                }
             try {
                   negative = (float) d.child("Negative").getChildrenCount();
                  }catch (Exception e){

                   }

                m.setTotal(positive+negative+"");

                try {
                    percent =(positive/(positive+negative))*100;

                }
               catch (Exception e){

              }
              try {
                  m.setComment(d.child("Comment").getChildrenCount()+0+"");
              } catch (Exception e){

              }

              if(d.child("Positive").child(uid).exists() || d.child("Negative").child(uid).exists()){
                  m.setVoted("1");
              }
              else{
                  m.setVoted("0");
              }
                if(percent > 50){
                  color = 1;
                }else if(percent == 50){
                    color = 2;
                }else if(percent < 50){
                    color = -1;
                }
                m.setColor(color+"");//color 1 means green -1 means red 0 means white(no vote) 2 means yellow
                m.setPositive(percent+"");
                thoughts.add(m);// its name of image in database
            }catch (Exception e){

            }
        }




        return thoughts;

    }
}
