package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
        final LinearLayoutManager lmanager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lmanager);

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
                pd.setCancelable(true);
                pd.show();
                if(thoughtedit.getText().toString().trim().equals("")){
                    pd.dismiss();
                    Toast.makeText(getActivity(),"Write Something before posting..,",Toast.LENGTH_SHORT).show();
                }
       /*         else if(currentUser.getDisplayName()==null){

                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setMessage("Post is anonymous for only other users not administrators, User will see the post by the name you enter here (You can set this ONLY ONE time) ");
                    ad.setTitle("Choose a display name");

                    final EditText input = new EditText(getActivity());
// Specify the type of input expected
                    ad.setView(input);

// Set up the buttons
                    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           String m_Text = input.getText().toString();
                           if(m_Text.length()>12){
                               Toast.makeText(getContext(),"Display name should be less than 12 characters",Toast.LENGTH_LONG).show();
                               input.setText("");

                           }
                           else{
                               if(currentUser!=null){
                                   UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                           .setDisplayName(m_Text).build();
                                   currentUser.updateProfile(profileUpdates).addOnSuccessListener(getActivity(),new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           final long time = System.currentTimeMillis();
                                           mpollsRef.child(time+"").child("Thought").setValue(thoughtedit.getText().toString().trim());
                                           mpollsRef.child(time+"").child("displayname").setValue(currentUser.getDisplayName());
                                           mpollsRef.child(time+"").child("UID").setValue(firebaseAuth.getUid()).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                               @Override
                                               public void onSuccess(Void aVoid) {
                                                   Toast.makeText(getActivity(),"Successfully posted ! ",Toast.LENGTH_SHORT).show();
                                                   thoughtedit.getText().clear();
                                                   pd.dismiss();
                                                   Intent i = new Intent(getActivity(),drawer.class);
                                                   Bundle b = new Bundle();
                                                   b.putString("f","polls");
                                                   i.putExtras(b);
                                                   startActivity(i);
                                                   getActivity().finish();
                                               }
                                           });
                                       }
                                   });



                        }
                           }
                        }
                    });
                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    ad.show();

                }*/ else
                    {
                        final long time = System.currentTimeMillis();
                        mpollsRef.child(time + "").child("Thought").setValue(thoughtedit.getText().toString().trim());
                  //      mpollsRef.child(time+"").child("displayname").setValue(currentUser.getDisplayName());
                        mpollsRef.child(time + "").child("UID").setValue(firebaseAuth.getUid()).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Successfully posted ! ", Toast.LENGTH_SHORT).show();
                                thoughtedit.getText().clear();
                                pd.dismiss();
                                Intent i = new Intent(getActivity(), drawer.class);
                                Bundle b = new Bundle();
                                // used in cloud messaging
                                b.putString("f", "polls");
                                i.putExtras(b);
                                startActivity(i);
                                getActivity().finish();
                            }


                        });

                    }
                }


        });

        mpollsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    long time = System.currentTimeMillis()-Long.parseLong(d.getKey());
                    long days = TimeUnit.MILLISECONDS.toDays(time);
                    try {
                        if (d.child("UID").getValue().toString().equals(currentUser.getUid())) {
                            if (days < 1) {
                                button.setVisibility(View.INVISIBLE);
                                thoughtedit.setText("You can only post one thought per day.");
                                thoughtedit.setFocusable(false);
                                thoughtedit.setClickable(false);
                            } else {
                                button.setVisibility(View.VISIBLE);
                                thoughtedit.setFocusable(true);
                                thoughtedit.setClickable(true);
                            }
                        }
                        if (days > 30) {                                                          // avoiding / stop growing of database
                            mpollsRef.child(d.getKey()).removeValue();
                        }
                    }catch (Exception e){}
                }
                    adapter = new suggestionAdapter(getActivity(), thoughtCollection.getthoughtcollection(dataSnapshot, currentUser.getUid()));
                int pos = getArguments().getInt("Initial_pos");

                lmanager.scrollToPosition(pos);
                rv.setAdapter(adapter);
                Toast.makeText(getContext(),pos+"",Toast.LENGTH_LONG).show();
                pd1.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                         Toast.makeText(getActivity(),"Your Don't have permission to be here!",Toast.LENGTH_LONG).show();
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
                        Intent i = new Intent(getActivity(),drawer.class);
                        Bundle b = new Bundle();
                        b.putString("f","polls");
                        b.putInt("position",position);
                        i.putExtras(b);
                        startActivity(i);
                        getActivity().finish();

                    }
                });
                ab.setNegativeButton("No, It's rubbish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mpollsRef.child(thoughts.get(position).getTimestamp()).child("Negative").child(currentUser.getUid()).setValue("1");
                        mpollsRef.child(thoughts.get(position).getTimestamp()).child("Positive").child(currentUser.getUid()).removeValue();
                        Intent i = new Intent(getActivity(),drawer.class);
                        Bundle b = new Bundle();
                        b.putString("f","polls");
                        b.putInt("position",position);
                        i.putExtras(b);
                        startActivity(i);
                        getActivity().finish();

                    }
                });
              ab.setNeutralButton("Comment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                           Intent i = new Intent(getActivity(),Comment.class);
                        Bundle b = new Bundle();
                        b.putInt("position",position);
                        b.putString("timestamp",thoughts.get(position).getTimestamp());
                        i.putExtras(b);
                            startActivity(i);
                           getActivity().finish();
                    }
                });
                ab.show();
            }
            }));
        return view;
    }

}

