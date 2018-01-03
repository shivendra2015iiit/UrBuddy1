package com.shivendra.hp.urbuddy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shivendra.hp.urbuddy.galleryData.photoCollection;
import com.shivendra.hp.urbuddy.galleryRecycler.galleryAdapter;
import com.shivendra.hp.urbuddy.mRecycler.RecyclerItemClickListener;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Shivendra HP on 05-08-2017.
 */

public class gallery extends Fragment {
    private DatabaseReference mRootRef;
    galleryAdapter adapter;
    RecyclerView rv;
    ProgressDialog pd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Gallery");



    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.gallery,container,false);

        pd= new ProgressDialog(getActivity());
        pd.setMessage("Loading ...");
        pd.setCancelable(true);
        pd.show();
        rv = (RecyclerView) v.findViewById(R.id.rv_gallery);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/GalleryData");

        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter = new galleryAdapter(getActivity(), photoCollection.getphotocollection(dataSnapshot));
                rv.setAdapter(adapter);
                pd.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"Database errorGallery2: " +databaseError,Toast.LENGTH_SHORT).show();
            }
        });

        ///
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference imagesRef;
                imagesRef = storageRef.child("gallery/"+photoCollection.photos.get(position).getPhotoUrl());
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;
                int height = displaymetrics.heightPixels;
                loadPhoto(imagesRef,width,height);
            }
        }));
        return  v;
    }
    private void loadPhoto(StorageReference reference, int width, int height) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.setContentView(R.layout.custom_fullimage_dialog);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_fullimage_dialoge,
                (ViewGroup) getView().findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        Glide.with(this).using(new FirebaseImageLoader()).load(reference).placeholder(R.drawable.loading).into(image);
        PhotoViewAttacher pd = new PhotoViewAttacher(image);
        pd.setZoomable(true);
        image.getLayoutParams().height = height;
        image.getLayoutParams().width = width;
        image.requestLayout();
        dialog.setContentView(layout);
        dialog.show();
    }
}
