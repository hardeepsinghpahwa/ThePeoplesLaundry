package com.example.hardeep.myproject.admin.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Send_notification_to_user extends AppCompatActivity {

    WebView mWebview;
    DatabaseReference reference;
    TextView token;
    String tokenid;
    Button click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification_to_user);

        mWebview=findViewById(R.id.firebaseweb);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        mWebview .loadUrl("https://console.firebase.google.com/project/myproject-f11fb/notification");


        token=findViewById(R.id.tokenid);
        final String user_id=getIntent().getStringExtra("u_id");
        reference= FirebaseDatabase.getInstance().getReference().child("1").child("User details");


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        show(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


            private void show(DataSnapshot dataSnapshot) {

                    String tokenuser=dataSnapshot.child(user_id).child("instant_id").getValue(String.class);
                    token.setText(tokenuser);

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();


}
}
