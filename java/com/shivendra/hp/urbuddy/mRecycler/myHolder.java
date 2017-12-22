package com.shivendra.hp.urbuddy.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivendra.hp.urbuddy.R;

/**
 * Created by Shivendra HP on 19-12-2017.
 */

public class myHolder extends RecyclerView.ViewHolder {

    TextView dayname;
    TextView snackscount;
    TextView teacount;
    ImageView snacksimage;

    public myHolder(View itemView) {
        super(itemView);

        dayname = (TextView) itemView.findViewById(R.id.dayName);
        snacksimage = (ImageButton) itemView.findViewById(R.id.dayMenu);
        snackscount = (TextView) itemView.findViewById(R.id.snacks);
        teacount = (TextView) itemView.findViewById(R.id.tea);

    }
}
