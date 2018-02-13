package com.shivendra.hp.urbuddy.commentRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shivendra.hp.urbuddy.R;

/**
 * Created by Shivendra HP on 11-02-2018.
 */

public class commentHolder  extends RecyclerView.ViewHolder {
    TextView comment;
    TextView commentdate;
    public commentHolder(View itemView) {
        super(itemView);
        comment = (TextView)itemView.findViewById(R.id.comment);
        commentdate =(TextView)itemView.findViewById(R.id.commentdate);

    }
}
