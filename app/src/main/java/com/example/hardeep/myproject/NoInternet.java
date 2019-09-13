package com.example.hardeep.myproject;

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NoInternet extends AppCompatActivity {

    Button retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        retry=findViewById(R.id.retrybutton);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInternetAvailable())
                {
                    finish();
                }
                else
                {
                    Toast.makeText(NoInternet.this,"Turn On Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }
}
