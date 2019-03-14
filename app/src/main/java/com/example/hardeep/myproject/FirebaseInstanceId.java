package com.example.hardeep.myproject;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Hardeep on 04-04-2018.
 */

public class FirebaseInstanceId extends FirebaseInstanceIdService {

    private static final String TAG="MyFirebaseId";
    DatabaseReference databaseReference;

    @Override
    public void onTokenRefresh() {

        String token= com.google.firebase.iid.FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token"+token);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("3").child("Notifications").child("token").setValue(token);


    }
}
