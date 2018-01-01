package com.shivendra.hp.urbuddy.galleryRecycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shivendra.hp.urbuddy.R;
import com.shivendra.hp.urbuddy.galleryData.photo;
import com.shivendra.hp.urbuddy.mData.menu;

import java.util.ArrayList;

/**
 * Created by Shivendra HP on 27-12-2017.
 */

public class galleryAdapter extends RecyclerView.Adapter<galleryHolder> {

    Context c;
    ArrayList<photo> Photo;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef;

    public galleryAdapter(Context c ,ArrayList<photo> Photo) {
        this.c = c;
        this.Photo = Photo;

    }
    @Override
    public galleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_rv,parent,false);
        galleryHolder holder = new galleryHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final galleryHolder holder, int position) {

        imagesRef = storageRef.child("gallery/"+Photo.get(position).getPhotoUrl());
          holder.name.setText(Photo.get(position).getName());
        Glide.with(c).using(new FirebaseImageLoader()).load(imagesRef).placeholder(R.drawable.loading).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return Photo.size();
    }
}
