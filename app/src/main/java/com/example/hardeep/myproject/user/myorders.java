package com.example.hardeep.myproject.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.hardeep.myproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myorders extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    String userid,uid;
    RecyclerView recyclerView;
    ArrayList<String> orders,costs,statuses;
    RecyclerView.Adapter adapter;
    String order_id,amount;
    ProgressBar progressBar;
    DatabaseReference databaseReference,dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        userid=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("1");
        dataref=FirebaseDatabase.getInstance().getReference().child("2").child("Accepted Orders");

        orders=new ArrayList<>();
        costs=new ArrayList<>();
        statuses=new ArrayList<>();


        recyclerView=findViewById(R.id.user_order_recycler_view);
        progressBar=findViewById(R.id.progressbarmyorders);
        recyclerView.setLayoutManager(new LinearLayoutManager(myorders.this));
        adapter=new OrderAdapter(orders,costs,statuses,myorders.this,progressBar);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();


        orders.clear();
        costs.clear();
        statuses.clear();


        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    uid=d.child("userid").getValue(String.class);

                    if(userid.equals(uid))
                    {
                        order_id=d.getRef().getKey();
                        amount=d.child("totalamount").getValue(String.class);
                        orders.add("#"+order_id);
                        costs.add("INR "+amount);
                        statuses.add(d.child("status").getValue(String.class));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out, R.anim.right_in);
    }
}
