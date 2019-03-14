package com.example.hardeep.myproject.admin;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.hardeep.myproject.user.fragments.About_us;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.user.fragments.Rateus;
import com.example.hardeep.myproject.admin.fragments.Firebase_web;
import com.example.hardeep.myproject.admin.fragments.admin_all_orders;
import com.example.hardeep.myproject.admin.fragments.admin_all_users;
import com.example.hardeep.myproject.admin.fragments.admin_tools;

public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    int idm = R.id.allorders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Fragment frag=null;
        frag=new admin_all_orders();
        if(frag!=null) {
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.adminframe, frag);
            fr.commit();
        }
        drawerLayout = findViewById(R.id.drawlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = findViewById(R.id.adminnavview);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment f=null;
        int id=item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        if(id==R.id.rateus)
        {
            f=new About_us();
        }
        else if(id==R.id.aboutus)
        {
            f=new Rateus();
        }

        FragmentTransaction fr= getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.adminframe,f);
        fr.commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawlayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.allorders) {
            fragment = new admin_all_orders();
        } else if (id == R.id.allusers) {
            fragment = new admin_all_users();
        }
        else if(id==R.id.tools){
            fragment=new admin_tools();
        }
        else if(id==R.id.firebase)
        {
            fragment=new Firebase_web();
        }

            FragmentTransaction fr= getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.adminframe,fragment);
            fr.commit();

        DrawerLayout drawer = findViewById(R.id.drawlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

