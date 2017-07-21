package com.shivendra.hp.urbuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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
        profilepic = (ImageView)header.findViewById(R.id.userdp);
        displayname = (TextView) header.findViewById(R.id.dname);
        mail = (TextView)header.findViewById(R.id.email);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mess) {
            // Handle the camera action
        } else if (id == R.id.nav_acadmic) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_hostel) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_contactus) {

        }else if(id==R.id.nav_logout){
            LogOut(getCurrentFocus());
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

}
