package com.shivendra.hp.urbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.shivendra.hp.urbuddy.commentData.commentCollection;
import com.shivendra.hp.urbuddy.commentRecycler.commentAdapter;
import com.shivendra.hp.urbuddy.mData.menuCollection;
import com.shivendra.hp.urbuddy.mRecycler.myAdapter;

public class Comment extends AppCompatActivity {
    private DatabaseReference mpollsRef;
    private DatabaseReference mpollsRef2;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    commentAdapter adapter;
    RecyclerView rv ;
    private String[] user;
    TextView thought;
CollapsingToolbarLayout collapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        thought =(TextView)findViewById(R.id.thoughtOndiscussion);


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {


            firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        user = currentUser.getEmail().split("@");
        mpollsRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/Polls");


        Bundle bundle = getIntent().getExtras();
        final String timestamp = bundle.getString("timestamp");

            mpollsRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/Polls/"+timestamp+"/Comment");
            rv = (RecyclerView) findViewById(R.id.rv);
            rv.setLayoutManager(new LinearLayoutManager(this));

            mpollsRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    adapter = new commentAdapter(getApplicationContext(), commentCollection.getcollection(dataSnapshot));
                    rv.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        mpollsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thought.setText(dataSnapshot.child(timestamp).child("Thought").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            final ProgressDialog pd  = new ProgressDialog(this);
            final EditText editText = (EditText)findViewById(R.id.messageedit);
            Button postbutton = (Button)findViewById(R.id.postbutton);

            postbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd.setMessage("Posting ... ");
                    pd.show();

                    if(editText.getText().toString().trim().equals("")){
                        Toast.makeText(getApplication(),"Write something to comment",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    else {
                        mpollsRef.child(timestamp).child("Comment").child(System.currentTimeMillis() + "").setValue(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                editText.setText("");
                                pd.dismiss();
                                Toast.makeText(getApplication(),"Posted successfully",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = (EditText)findViewById(R.id.messageedit);
                Button postbutton = (Button)findViewById(R.id.postbutton);
                if(editText.getVisibility() == View.VISIBLE)
                {

                    postbutton.setVisibility(View.INVISIBLE);
                    editText.setVisibility(View.INVISIBLE);
                }
                else {
                    postbutton.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                }

            }
        });*/
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle("Swipe up");
    }else{
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
    }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(Comment.this, drawer.class);
        Bundle b = new Bundle();
        b.putString("f","polls");
       i.putExtras(b);
        startActivity(i);
        Comment.this.finish();

    }
}
