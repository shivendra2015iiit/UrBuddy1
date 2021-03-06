package com.shivendra.hp.urbuddy;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import uk.co.senab.photoview.PhotoViewAttacher;

public class drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

         private FirebaseAuth firebaseAuth;
         private FirebaseStorage storage = FirebaseStorage.getInstance();
         private StorageReference storageReference =storage.getReference();

         private DatabaseReference dbr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ur-buddy.firebaseio.com/messmenu");
         private StorageReference messmenureference;
         ImageView profilepic ;
         TextView displayname;
         TextView mail;
          int scrollPos=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//ad banner//
     //
        FirebaseMessaging.getInstance().subscribeToTopic("notification_to_all");  //for sending notification to all

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
        Fragment fragment = null;
        fragment = new home();
        ///setting default fragment to home
        Bundle bundle = getIntent().getExtras();
        try {
            try {
                scrollPos = bundle.getInt("position");
            }catch (Exception e){}

            Bundle args = new Bundle();
            args.putInt("Initial_pos", scrollPos);

            String position = bundle.getString("f");

            if(position!=null){
                if(position.equals("polls")){
                    fragment = new polls();
                fragment.setArguments(args);
                }
                else if(position.equals("mess"))
                    fragment = new mess();
                else if(position.equals("gallery")){
                    fragment = new gallery();
                }
            }

        }catch (Exception e){

        }


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
//scroll

        Bundle args = new Bundle();
        args.putInt("Initial_pos", scrollPos);
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

        }
        else if(id == R.id.nav_events) {
              fragment = new EventsNotification();
          }/*else if (id == R.id.nav_upload) {

          }*/
           /*else if (id == R.id.nav_hostel) {     // when we add hostel fragment in future
              fragment = new hostel();

        }*/
        else if(id == R.id.nav_rewardvideo) {

              Intent i = new Intent(drawer.this,RewardedAdd.class);
              startActivity(i);
              drawer.this.finish();

          }else if(id ==R.id.nav_suggestview){
              fragment = new polls();
              fragment.setArguments(args);
          }

        else if (id == R.id.nav_share) {
              try {
                  ShareCompat.IntentBuilder.from(drawer.this)
                          .setType("text/plain")
                          .setChooserTitle("Chooser title")
                          .setText("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())
                          .startChooser();
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
        final int width = displaymetrics.widthPixels;
        final int height = displaymetrics.heightPixels;

        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                messmenureference = storageReference.child(dataSnapshot.child("Photoname").getValue().toString());
                loadPhoto(messmenureference,width,height);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplication(),"ERROR  "+databaseError,Toast.LENGTH_LONG).show();
            }
        });

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

        Glide.with(this).using(new FirebaseImageLoader()).load(reference).thumbnail(0.1f).placeholder(R.drawable.loading).into(image);
        PhotoViewAttacher pd = new PhotoViewAttacher(image);
        pd.setZoomable(true);
        image.getLayoutParams().height = height;
        image.getLayoutParams().width = width;
        image.requestLayout();
        dialog.setContentView(layout);
        dialog.show();

    }



}
