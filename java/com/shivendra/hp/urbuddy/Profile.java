package com.shivendra.hp.urbuddy;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Profile extends AppCompatActivity {

    String imagename="dpoption7";
    ImageView displaypic;
    EditText displayname;
    FirebaseUser user ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);
          displaypic =(ImageView)findViewById(R.id.displaypic) ;
          displayname =(EditText)findViewById(R.id.displayname);
          user=FirebaseAuth.getInstance().getCurrentUser();

        //setting status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.green));


    }

    public void onclickdpoption1(View v){
        displaypic.setImageResource(R.drawable.dpoption4);
        imagename="dpoption4";
    }
    public void onclickdpoption2(View v){
        displaypic.setImageResource(R.drawable.dpoption1);
        imagename="dpoption1";
    }
    public void onclickdpoption3(View v){
        displaypic.setImageResource(R.drawable.dpoption2);
        imagename="dpoption2";
    }
    public void onclickdpoption4(View v){
        displaypic.setImageResource(R.drawable.dpoption3);
        imagename="dpoption3";
    }
    public void onclickdpoption5(View v){
        displaypic.setImageResource(R.drawable.dpoption5);
        imagename="dpoption5";
    }
    public void onclickdpoption6(View v){
        displaypic.setImageResource(R.drawable.dpoption6);
        imagename="dpoption6";
    }
    public void onclickdpoption7(View v){
        displaypic.setImageResource(R.drawable.dpoption7);
        imagename="dpoption7";
    }
    public void onclickdpoption8(View v){
        displaypic.setImageResource(R.drawable.dpoption8);
        imagename="dpoption8";
    }

    public void onclicksave(final View v){
        String dname = displayname.getText().toString().trim();
        if(TextUtils.isEmpty(dname)){
            Snackbar.make(v,"Please provide your display name",Snackbar.LENGTH_SHORT).show();
            return;
        }

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(dname)
                .setPhotoUri(Uri.parse("android.resource:/com.shivendra.hp.urbuddy/drawable/"+imagename))
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(v,"Successfully Updated",Snackbar.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //debug text
                            Snackbar.make(v,"Error in update Updated",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent I = new Intent(getApplicationContext(),drawer.class) ;
        startActivity(I);
        Profile.this.finish();
    }
    public void cancel(final View view){
        Intent I = new Intent(getApplicationContext(),drawer.class) ;
        startActivity(I);
        Profile.this.finish();
    }
}

