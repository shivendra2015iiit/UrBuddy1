package com.shivendra.hp.urbuddy.galleryData;

import com.google.firebase.database.DataSnapshot;
import com.shivendra.hp.urbuddy.mData.menu;

import java.util.ArrayList;

/**
 * Created by Shivendra HP on 27-12-2017.
 */

public class photoCollection {
    public final static ArrayList<photo> photos = new ArrayList<>();
    public static ArrayList<photo> getphotocollection(DataSnapshot ds) {
        photos.clear();
        photo m = new photo();




        for (DataSnapshot d : ds.getChildren()){
            try {
                m = new photo();
                m.setName(d.getKey());
                m.setPhotoUrl(d.getValue().toString());
                photos.add(m);// its name of image in database
            }catch (Exception e){

            }
        }
        return photos;
    }
}
