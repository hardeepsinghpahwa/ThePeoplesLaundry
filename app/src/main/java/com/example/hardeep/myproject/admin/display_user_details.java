package com.example.hardeep.myproject.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.get_details;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class display_user_details extends AppCompatActivity {

    String user_id;
    DatabaseReference databaseReference;
    TextView uname,uusername,uemail;
    private TextView namee;
    ImageView circleImageView;
    ImageView imageView;
    int i=0;
    EditText notimessage;
    ProgressBar progressBar;
    Button notisend;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_details);

        uusername=findViewById(R.id.username1);
        uname=findViewById(R.id.name1);
        uemail=findViewById(R.id.email1);
        circleImageView=findViewById(R.id.imageprofile1);
        notimessage=findViewById(R.id.notitext);
        notisend=findViewById(R.id.notibutton);
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressbar2);

        Intent i=getIntent();
        user_id=getIntent().getStringExtra("u_id");


        databaseReference=FirebaseDatabase.getInstance().getReference().child("1");
        databaseReference.child(user_id).child("username").getKey();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    get_details get = new get_details();
                    get.setUsername(d.child(user_id).getValue(get_details.class).getUsername());
                    get.setName(d.child(user_id).getValue(get_details.class).getName());
                    get.setEmail(d.child(user_id).getValue(get_details.class).getEmail());
                    get.setImage(d.child(user_id).getValue(get_details.class).getImage());


                    uname.setText(get.getName());
                    uemail.setText(get.getEmail());
                    Picasso.with(display_user_details.this).load(get.getImage()).centerCrop().fit().into(circleImageView);
                    uusername.setText(get.getUsername());


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
