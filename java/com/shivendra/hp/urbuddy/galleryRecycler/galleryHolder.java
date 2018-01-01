package com.shivendra.hp.urbuddy.galleryRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivendra.hp.urbuddy.R;

/**
 * Created by Shivendra HP on 27-12-2017.
 */

public class galleryHolder extends RecyclerView.ViewHolder {
    TextView name;
    ImageView photo;

    public galleryHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.gallery_txt);
        photo = (ImageView) itemView.findViewById(R.id.gallery_img);
    }

}
