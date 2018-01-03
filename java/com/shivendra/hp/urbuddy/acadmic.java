package com.shivendra.hp.urbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Shivendra HP on 05-08-2017.
 */

public class acadmic extends Fragment {
    ImageView ap;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.academic);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.acadmic,container,false);
         ap = (ImageView) v.findViewById(R.id.annualplanner);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(ap);
        pAttacher.update();

        return v;
    }
}
