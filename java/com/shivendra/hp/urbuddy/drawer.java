package com.shivendra.hp.urbuddy;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import uk.co.senab.photoview.PhotoViewAttacher;

public class drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

         private FirebaseAuth firebaseAuth;
         private FirebaseStorage storage = FirebaseStorage.getInstance();
         private StorageReference storageReference =storage.getReference();
         private StorageReference messmenureference = storageReference.child("messmenu.jpg");
         ImageView profilepic ;
         TextView displayname;
         TextView mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//ad banner//
     //


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
        displayname.setText(R.string.title_activity_splashscreen);
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
    private static final int TIME_INTERVAL = 1500; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                super.onBackPressed();
                return;
            }
            else { Toast.makeText(getBaseContext(), "Tap back button once again exit", Toast.LENGTH_SHORT).show(); }

            mBackPressed = System.currentTimeMillis();


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

        } /*else if (id == R.id.nav_hostel) {     // when we add hostel fragment in future
              fragment = new hostel();

        }*/
        else if(id == R.id.nav_rewardvideo) {

              Intent i = new Intent(drawer.this,RewardedAdd.class);
              startActivity(i);
              drawer.this.finish();

          }else if(id ==R.id.nav_suggestview){
              fragment = new polls();
          }

        else if (id == R.id.nav_share) {
              try {
                  Intent i = new Intent(Intent.ACTION_SEND);
                  i.setType("text/plain");
                  i.putExtra(Intent.EXTRA_SUBJECT, "Ur Buddy V 2.1.1");
                  String sAux ;
                  sAux ="https://drive.google.com/open?id=1UTx5XuIPV7VcGP-LL97eP8-JSXm_Ujzm";
                  i.putExtra(Intent.EXTRA_TEXT, sAux);
                  startActivity(Intent.createChooser(i, "choose one"));
              } catch(Exception e) {
                  //e.toString();
              }

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
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        loadPhoto(messmenureference,width,height);
    }
    public void booksnacks(View v){
        Intent i = new Intent(drawer.this,Booksnacks.class);
        startActivity(i);
        drawer.this.finish();

    }

    private void loadPhoto( StorageReference reference,int width, int height) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.setContentView(R.layout.custom_fullimage_dialog);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_fullimage_dialoge,
                (ViewGroup) findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(this).using(new FirebaseImageLoader()).load(reference).placeholder(R.drawable.loading).into(image);
        PhotoViewAttacher pd = new PhotoViewAttacher(image);
        pd.setZoomable(true);
        image.getLayoutParams().height = height;
        image.getLayoutParams().width = width;
        image.requestLayout();
        dialog.setContentView(layout);
        dialog.show();

    }



}
