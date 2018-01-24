package com.shivendra.hp.urbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Shivendra HP on 05-08-2017.
 */

public class acadmic extends Fragment {
    ImageView ap;
    ImageView ap1;
    ImageView ttfirst;  //Time table first year
    ImageView ttsecond;
    ImageView ttthird;
    ImageView ttfourth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef;
    StorageReference imagesRef1;
    StorageReference imagesRef2;
    StorageReference imagesRef3;
    StorageReference imagesRef4;
    StorageReference imagesRef5;
    private DatabaseReference mRootRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.academic);
        mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/acadmic");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.acadmic,container,false);
         ap = (ImageView) v.findViewById(R.id.annualplanner);
        ap1 =(ImageView) v.findViewById(R.id.annualplanner1);
        Toast.makeText(getContext(),"Pinch to Zoom ,Scroll down for more",Toast.LENGTH_LONG).show();
        ttfirst =(ImageView) v.findViewById(R.id.ttfirst);
        ttsecond = (ImageView) v.findViewById(R.id.ttsecond);
        ttthird =(ImageView) v.findViewById(R.id.ttthird);
        ttfourth = (ImageView) v.findViewById(R.id.ttfourth);

         mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 try {

                     imagesRef = storageRef.child("acadmics/"+dataSnapshot.child("annualP1").getValue());
                     Glide.with(getContext()).using(new FirebaseImageLoader()).load(imagesRef).into(ap);
                 }catch (Exception e){

                 }try {
                     imagesRef1 = storageRef.child("acadmics/"+dataSnapshot.child("annualP2").getValue());
                     Glide.with(getContext()).using(new FirebaseImageLoader()).load(imagesRef1).into(ap1);
                 }catch (Exception e){

                 } try {
                     imagesRef2 = storageRef.child("acadmics/"+dataSnapshot.child("timeT1").getValue());
                     Glide.with(getContext()).using(new FirebaseImageLoader()).load(imagesRef2).into(ttfirst);
                 }catch (Exception e){

                 }     try {
                     imagesRef3 = storageRef.child("acadmics/"+dataSnapshot.child("timeT2").getValue());
                     Glide.with(getContext()).using(new FirebaseImageLoader()).load(imagesRef3).into(ttsecond);
                 }catch (Exception e){

                 }          try {
                     imagesRef4 = storageRef.child("acadmics/"+dataSnapshot.child("timeT3").getValue());
                     Glide.with(getContext()).using(new FirebaseImageLoader()).load(imagesRef4).into(ttthird);
                 }catch (Exception e){

                 }             try {
                     imagesRef5 = storageRef.child("acadmics/"+dataSnapshot.child("timeT4").getValue());
                     Glide.with(getContext()).using(new FirebaseImageLoader()).load(imagesRef5).into(ttfourth);
                 }catch (Exception e){

                 }

                 PhotoViewAttacher pAttacher;
                 pAttacher = new PhotoViewAttacher(ap);
                 pAttacher.update();
                 PhotoViewAttacher pAttacher1;
                 pAttacher1 = new PhotoViewAttacher(ap1);
                 pAttacher1.update();
                 PhotoViewAttacher pAttacher2;
                 pAttacher2 = new PhotoViewAttacher(ttfirst);
                 pAttacher2.update();
                 PhotoViewAttacher pAttacher3;
                 pAttacher3= new PhotoViewAttacher(ttsecond);
                 pAttacher3.update();
                 PhotoViewAttacher pAttacher4;
                 pAttacher4 = new PhotoViewAttacher(ttthird);
                 pAttacher4.update();
                 PhotoViewAttacher pAttacher5;
                 pAttacher5 = new PhotoViewAttacher(ttfourth);
                 pAttacher5.update();
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });


        return v;
    }
}
