package com.shivendra.hp.urbuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

         private FirebaseAuth firebaseAuth;
         ImageView profilepic ;
         TextView displayname;
         TextView mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Feedback", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        firebaseAuth = FirebaseAuth.getInstance();
        profilepic = (ImageView) header.findViewById(R.id.userdp);
        displayname = (TextView) header.findViewById(R.id.dname);
        mail = (TextView)header.findViewById(R.id.email);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        String mail_id = currentUser.getEmail();


        profilepic.setImageResource(R.drawable.dpoption8);
        displayname.setText("Ur Buddy");
        mail.setText(mail_id);

        // for time being no personalization

      /*
        String uri =currentUser.getPhotoUrl().toString();
       int a= uri.indexOf("/");                                               //retrived  uri have one less "/" that is why adding it;
        uri = uri.substring(0,a)+"/"+uri.substring(a,uri.length());
        Uri dpic=Uri.parse(uri);
        String Uname =currentUser.getDisplayName();
        String mail_id = currentUser.getEmail();




profilepic.setOnClickListener(new View.OnClickListener(){
    @Override
    public  void onClick(View v){
        Intent I = new Intent(getApplicationContext(),Profile.class) ;
        startActivity(I);
        drawer.this.finish();
    }
});

        if(dpic != null && Uname!=null && mail_id!=null && dpic!=null) {

            profilepic.setImageURI(dpic);
            displayname.setText(Uname);
            mail.setText(mail_id);
        }





        */
        //




        ///setting default fragment to home
        Fragment fragment = null;
        fragment = new home();
        if(fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame,fragment);
            ft.commit();
        }




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

           super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();
          if(id == R.id.nav_home){
              fragment = new home();
        }
        else if (id == R.id.nav_mess) {
              fragment = new mess();
        } else if (id == R.id.nav_acadmic) {
              fragment = new acadmic();
        } else if (id == R.id.nav_gallery) {
              fragment = new gallery();

        } else if (id == R.id.nav_hostel) {
              fragment = new hostel();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_contactus) {
            fragment = new contactus();

        }else if(id==R.id.nav_logout){
            LogOut(getCurrentFocus());
        }
        if(fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LogOut(View v) {
        firebaseAuth.signOut();
        Intent I = new Intent(getApplicationContext(),LoginActivity.class) ;  //loging activity
        startActivity(I);
        drawer.this.finish();
    }

    public void showmenu(View v){

    }
    public void booksnacks(View v){
        Intent i = new Intent(drawer.this,Booksnacks.class);
        startActivity(i);
        drawer.this.finish();

    }

}
