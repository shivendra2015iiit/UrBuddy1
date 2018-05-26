package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivendra.hp.urbuddy.HomeRecycler.homeAdapter;
import com.shivendra.hp.urbuddy.galleryData.photoCollection;
import com.shivendra.hp.urbuddy.galleryRecycler.galleryAdapter;
import com.shivendra.hp.urbuddy.homeData.panelCollection;

/**
 * Created by Shivendra HP on 05-08-2017.
 */

public class home extends Fragment {
    private DatabaseReference mRootRef;
    homeAdapter adapter;
    RecyclerView rv;
    ProgressDialog pd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Home");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.home,container,false);
        pd= new ProgressDialog(getActivity());
        pd.setMessage("Loading ...");
        pd.setCancelable(true);
        pd.show();
        rv = (RecyclerView) v.findViewById(R.id.rv_home);
        final LinearLayoutManager lmanager =new LinearLayoutManager(getContext());
        rv.setLayoutManager(lmanager);
        rv.setNestedScrollingEnabled(false);
        mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/home");
        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapter = new homeAdapter(getActivity(), panelCollection.getpanelcollection(dataSnapshot));
                rv.setAdapter(adapter);
               /* lmanager.scrollToPosition(3);*/
                pd.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"Home : " +databaseError,Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }
}
