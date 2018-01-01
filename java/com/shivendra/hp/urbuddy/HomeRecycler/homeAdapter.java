package com.shivendra.hp.urbuddy.HomeRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shivendra.hp.urbuddy.R;
import com.shivendra.hp.urbuddy.homeData.panel;

import java.util.ArrayList;

/**
 * Created by Shivendra HP on 01-01-2018.
 */

public class homeAdapter extends RecyclerView.Adapter<homeHolder> {
    Context c;
    ArrayList<panel> tabs;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef;
    public homeAdapter(Context c, ArrayList<panel> tabs){
        this.c = c;
        this.tabs = tabs;
    }

    @Override
    public homeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv,parent,false);
        homeHolder holder = new homeHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(homeHolder holder, int position) {
            imagesRef = storageRef.child("home/"+tabs.get(position).getImageurl());
        try {
            holder.heading.setText(tabs.get(position).getHeading());
        }catch (Exception e){


        }
        try {
            holder.text.setText(tabs.get(position).getTexthome());
        }catch (Exception e){

        }
        try {
            Glide.with(c).using(new FirebaseImageLoader()).load(imagesRef).centerCrop().override(400, 200).into(holder.image);
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }
}
