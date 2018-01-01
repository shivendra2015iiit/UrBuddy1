package com.shivendra.hp.urbuddy.HomeRecycler;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivendra.hp.urbuddy.R;

/**
 * Created by Shivendra HP on 01-01-2018.
 */

public class homeHolder extends RecyclerView.ViewHolder {
    TextView text;
    TextView heading;
    ImageView image;
    public homeHolder(View itemview){
        super(itemview);
        heading = (TextView) itemview.findViewById(R.id.home_head);
        text = (TextView) itemview.findViewById(R.id.home_txt);
        image =(ImageView) itemview.findViewById(R.id.home_img);

    }
}
