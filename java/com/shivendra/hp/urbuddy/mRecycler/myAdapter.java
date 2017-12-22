package com.shivendra.hp.urbuddy.mRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shivendra.hp.urbuddy.Booksnacks;
import com.shivendra.hp.urbuddy.R;
import com.shivendra.hp.urbuddy.mData.menu;


import java.util.ArrayList;
/**
 * Created by Shivendra HP on 19-12-2017.
 */

public class myAdapter extends RecyclerView.Adapter<myHolder> {

    Context c;
    ArrayList<menu> Menu;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef;


    public myAdapter(Context c ,ArrayList<menu> Menu) {
        this.c = c;
        this.Menu = Menu;
    }



    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.snacks_rv,parent,false);
        myHolder holder = new myHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
              imagesRef = storageRef.child("snacksImage/"+Menu.get(position).getDayname()+".jpg");
               holder.dayname.setText(Menu.get(position).getDayname());
                holder.snackscount.setText(Menu.get(position).getSnackscount());
                holder.teacount.setText(Menu.get(position).getTeacount());
              //  Glide.with(c).using(new FirebaseImageLoader()).load(imagesRef).placeholder(R.drawable.placeholder).fitCenter().into(holder.snacksimage);
        Glide.with(c).using(new FirebaseImageLoader()).load(imagesRef).diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate().fitCenter().placeholder(R.drawable.loading).into(holder.snacksimage);
         //Glide.with(c).load("https://firebasestorage.googleapis.com/v0/b/ur-buddy.appspot.com/o/mail_id.png?alt=media&token=f2b004e4-64a4-460a-9241-7d5641683de2").into(holder.snacksimage);
    }

    @Override
    public int getItemCount() {
        return Menu.size();
    }
}
