package com.shivendra.hp.urbuddy.eventRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shivendra.hp.urbuddy.HomeRecycler.homeHolder;
import com.shivendra.hp.urbuddy.R;
import com.shivendra.hp.urbuddy.eventData.event;
import com.shivendra.hp.urbuddy.homeData.panel;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Shivendra HP on 09-02-2018.
 */

public class eventAdapter  extends RecyclerView.Adapter<eventHolder> {
    Context c;
    ArrayList<event> tabs;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef;
    public eventAdapter(Context c, ArrayList<event> tabs){
        this.c = c;
        this.tabs = tabs;
        Collections.reverse(this.tabs);
    }

    @Override
    public eventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_rv,parent,false);
        eventHolder holder = new eventHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(eventHolder holder, int position) {
        imagesRef = storageRef.child("events/"+tabs.get(position).getImageurl());
        try {
            holder.heading.setText(tabs.get(position).getHeading());
        }catch (Exception e){


        }
        try {
            holder.text.setText(tabs.get(position).getTextevent());
        }catch (Exception e){

        }
        try {
            Glide.with(c).using(new FirebaseImageLoader()).load(imagesRef).into(holder.image);
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }
}
