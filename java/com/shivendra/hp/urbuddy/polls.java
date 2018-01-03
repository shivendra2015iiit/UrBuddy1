package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivendra.hp.urbuddy.mRecycler.RecyclerItemClickListener;
import com.shivendra.hp.urbuddy.suggestionData.thoughtCollection;
import com.shivendra.hp.urbuddy.suggestionRecycler.suggestionAdapter;

import java.util.concurrent.TimeUnit;

import static com.shivendra.hp.urbuddy.suggestionData.thoughtCollection.thoughts;

/**
 * Created by Shivendra HP on 02-01-2018.
 */

public class polls extends Fragment {
    private DatabaseReference mpollsRef;
    private  FirebaseAuth firebaseAuth;
    RecyclerView rv;
    suggestionAdapter adapter;
    private FirebaseUser currentUser;
    private String[] user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Post a Thought Anonymously ");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        user = currentUser.getEmail().split("@");
        mpollsRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/Polls");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.polls,container,false);

        rv = (RecyclerView) view.findViewById(R.id.rv_suggestion);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        final ProgressDialog pd1 = new ProgressDialog(getActivity());
        pd1.setMessage("Loading...");
        pd1.setCancelable(true);
        pd1.show();
        final Button button = (Button) view.findViewById(R.id.button_post);
        final EditText thoughtedit = (EditText) view.findViewById(R.id.thought_post);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setMessage("Posting...");
                pd.setCancelable(false);
                pd.show();
                if(thoughtedit.getText().toString().trim().equals("")){
                    pd.dismiss();
                    Toast.makeText(getActivity(),"Write Something before posting..,",Toast.LENGTH_SHORT).show();
                }else{
                     final long time = System.currentTimeMillis();
                    mpollsRef.child(time+"").child("Thought").setValue(thoughtedit.getText().toString().trim());

                    mpollsRef.child(time+"").child("UID").setValue(firebaseAuth.getUid()).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(),"Successfully posted ! ",Toast.LENGTH_SHORT).show();
                        thoughtedit.getText().clear();
                        pd.dismiss();
                    }
                });

                }
            }
        });

        mpollsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    long time = System.currentTimeMillis()-Long.parseLong(d.getKey());
                    long days = TimeUnit.MILLISECONDS.toDays(time);
                    if(d.child("UID").getValue().toString().equals(currentUser.getUid())){
                        if(days<1){
                            button.setVisibility(View.INVISIBLE);
                            thoughtedit.setText("You can only post one thought per day.");
                            thoughtedit.setFocusable(false);
                            thoughtedit.setClickable(false);
                        }else{
                            button.setVisibility(View.VISIBLE);
                            thoughtedit.setFocusable(true);
                            thoughtedit.setClickable(true);
                        }
                    }
                        if(days>7){                                                          // avoiding for stop growing of database
                            mpollsRef.child(d.getKey()).removeValue();
                        }
                }
                    adapter = new suggestionAdapter(getActivity(), thoughtCollection.getthoughtcollection(dataSnapshot, currentUser.getUid()));
                    rv.setAdapter(adapter);
                pd1.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                AlertDialog.Builder ab= new AlertDialog.Builder(getActivity());
                ab.setTitle("Give Your View");
                ab.setMessage("Do you support this idea ? or You like the thought Shared ?");
                ab.setPositiveButton("Yes ,I do", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mpollsRef.child(thoughts.get(position).getTimestamp()).child("Negative").child(currentUser.getUid()).removeValue();
                       mpollsRef.child(thoughts.get(position).getTimestamp()).child("Positive").child(currentUser.getUid()).setValue("1");
                    }
                });
                ab.setNegativeButton("No, It's rubbish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mpollsRef.child(thoughts.get(position).getTimestamp()).child("Negative").child(currentUser.getUid()).setValue("1");
                        mpollsRef.child(thoughts.get(position).getTimestamp()).child("Positive").child(currentUser.getUid()).removeValue();

                    }
                });
                ab.show();
            }
            }));
        return view;
    }

}
