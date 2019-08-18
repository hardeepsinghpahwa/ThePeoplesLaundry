package com.example.hardeep.myproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hardeep.myproject.user.The_user_profile;
import com.google.common.util.concurrent.ServiceManager;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity

                if (isNetworkAvailable()) {

                    if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                    {
                        startActivity(new Intent(Splash.this, The_user_profile.class));
                        finish();
                    }else
                    {
                        Intent i = new Intent(Splash.this, Launcher.class);
                        startActivity(i);
                    }

                } else {
                    Intent i = new Intent(Splash.this, NoInternet.class);
                    startActivity(i);
                }


                // close this activity
                finish();
            }
        }, 3000);
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
