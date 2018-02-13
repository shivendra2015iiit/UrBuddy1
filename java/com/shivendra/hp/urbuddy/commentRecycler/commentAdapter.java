package com.shivendra.hp.urbuddy.commentRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shivendra.hp.urbuddy.R;
import com.shivendra.hp.urbuddy.commentData.comment;
import com.shivendra.hp.urbuddy.eventRecycler.eventHolder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Shivendra HP on 11-02-2018.
 */

public class commentAdapter  extends RecyclerView.Adapter<commentHolder>  {
    int i=0;
    Context c;
    ArrayList<comment> tabs;

    public commentAdapter(Context c, ArrayList<comment> tabs){
        this.c = c;
        this.tabs = tabs;

    }
    @Override
    public commentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_rv,parent,false);
        commentHolder holder = new commentHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(commentHolder holder, int position) {

                  holder.comment.setText( "\n\n"+tabs.get(position).getComment());


            holder.commentdate.setText((position+1)+" Posted at : "+tabs.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }
}
