package com.example.hardeep.myproject.user;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.hardeep.myproject.CompletedOrders;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.RejectedOrders;
import com.example.hardeep.myproject.get_details;
import com.example.hardeep.myproject.user.fragments.Active_orders;
import com.example.hardeep.myproject.user.fragments.Contact_us;
import com.example.hardeep.myproject.user.fragments.OrderRequest;
import com.example.hardeep.myproject.user.fragments.Previous_orders;
import com.example.hardeep.myproject.user.fragments.Settings;
import com.example.hardeep.myproject.user.fragments.Share_the_app;
import com.example.hardeep.myproject.user.fragments.price_list;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class The_user_profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CompletedOrders.OnFragmentInteractionListener, RejectedOrders.OnFragmentInteractionListener {

    private TextView name,email;
    CircleImageView image;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid,uri;
    String instantid;
    NavigationView navigationView;
    boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment frag=null;
        frag=new Active_orders();
        if(frag!=null) {
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.content, frag);
            fr.commit();
        }

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        name=header.findViewById(R.id.name);
        email=header.findViewById(R.id.email);
        firebaseAuth=FirebaseAuth.getInstance();
        userid=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("1");
        image=header.findViewById(R.id.circle_pic);

        instantid= com.google.firebase.iid.FirebaseInstanceId.getInstance().getToken();

        databaseReference.child("User details").child(userid).child("instant_id").setValue(instantid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                display(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void display(DataSnapshot dataSnapshot) {
        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            get_details d = new get_details();
            d.setName(dataSnapshot1.child(userid).getValue(get_details.class).getName());
            d.setEmail(dataSnapshot1.child(userid).getValue(get_details.class).getEmail());
            d.setImage(dataSnapshot1.child(userid).getValue(get_details.class).getImage());

            uri=d.getImage();
            name.setText(d.getName());
            email.setText(d.getEmail());
            Picasso.get().load(uri).fit().centerCrop().into(image);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.the_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aboutus) {

                return true;
            }
        else if(id==R.id.rateus)
            {

            }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment=null;
        int id = item.getItemId();

        if (id == R.id.activeorder) {
            fragment=new Active_orders();
        } else if (id == R.id.previousorders) {
            fragment=new Previous_orders();

        } else if (id == R.id.settings) {
            fragment=new Settings();

        } else if (id == R.id.share) {
            fragment=new Share_the_app();

        } else if (id == R.id.pricelist) {
            fragment= new price_list();
        }
        else if(id==R.id.profile){
            fragment=new user_profile_edit();
        }
        else if(id==R.id.contactus){
            fragment=new Contact_us();
        }
        else if(id==R.id.requestorder)
        {
            fragment=new OrderRequest();
        }

        FragmentTransaction f= getSupportFragmentManager().beginTransaction();
        f.replace(R.id.content,fragment);
        f.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish();
                return;
            }

            this.exit = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
