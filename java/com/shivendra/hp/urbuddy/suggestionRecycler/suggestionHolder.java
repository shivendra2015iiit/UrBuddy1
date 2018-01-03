package com.shivendra.hp.urbuddy.suggestionRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shivendra.hp.urbuddy.R;

/**
 * Created by Shivendra HP on 03-01-2018.
 */

public class suggestionHolder extends RecyclerView.ViewHolder {
    TextView thought;
    TextView details;
    RelativeLayout rl;
    public suggestionHolder(View itemView) {
        super(itemView);
        thought = (TextView) itemView.findViewById(R.id.thought);
        details = (TextView) itemView.findViewById(R.id.positive);
        rl = (RelativeLayout) itemView.findViewById(R.id.rl);


    }
}
