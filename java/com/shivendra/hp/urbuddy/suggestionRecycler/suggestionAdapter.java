package com.shivendra.hp.urbuddy.suggestionRecycler;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shivendra.hp.urbuddy.R;
import com.shivendra.hp.urbuddy.suggestionData.thought;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Shivendra HP on 03-01-2018.
 */

public class suggestionAdapter extends RecyclerView.Adapter<suggestionHolder> {
    Context c;
    ArrayList<thought> thoughts;
    public suggestionAdapter (Context c, ArrayList<thought> thoughts){
        this.c =c;
        this.thoughts= thoughts;
    }
    @Override
    public suggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_rv,parent,false);
        suggestionHolder holder = new suggestionHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(suggestionHolder holder, int position) {
             holder.thought.setText(thoughts.get(position).getThought());
        if(thoughts.get(position).getColor().equals("2")){
            holder.rl.setBackgroundColor(ContextCompat.getColor(c,R.color.yellow));

        }else if(thoughts.get(position).getColor().equals("1")){

            holder.rl.setBackgroundColor(ContextCompat.getColor(c,R.color.green));

        }else if(thoughts.get(position).getColor().equals("-1")){
            holder.rl.setBackgroundColor(ContextCompat.getColor(c,R.color.red));

        }
             holder.details.setText("Positive : "+thoughts.get(position).getPositive()+"%  Out of Total "+thoughts.get(position).getTotal()+" Votes");
    }

    @Override
    public int getItemCount() {
        return thoughts.size();
    }
}
