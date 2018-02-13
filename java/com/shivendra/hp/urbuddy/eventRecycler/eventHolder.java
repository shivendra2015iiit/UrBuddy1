package com.shivendra.hp.urbuddy.eventRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivendra.hp.urbuddy.R;

/**
 * Created by Shivendra HP on 09-02-2018.
 */

public class eventHolder extends RecyclerView.ViewHolder {
    TextView text;
    TextView heading;
    ImageView image;
    public eventHolder(View itemview){
        super(itemview);
        heading = (TextView) itemview.findViewById(R.id.home_head);
        text = (TextView) itemview.findViewById(R.id.home_txt);
        image =(ImageView) itemview.findViewById(R.id.home_img);

    }
}
