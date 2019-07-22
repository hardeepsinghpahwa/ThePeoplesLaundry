package com.example.hardeep.myproject.user;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.Main;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.get_details;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;


public class user_profile_edit extends Fragment {

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    CircleImageView circleImageView;
    TextView user_name, user_email;
    String user_id;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    Button signout;
    user_profile_edit user_profile_edit;

    public user_profile_edit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("1");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);

        circleImageView = v.findViewById(R.id.circleImage);
        user_name = v.findViewById(R.id.nameuser);
        user_email = v.findViewById(R.id.emailuser);
        signout=v.findViewById(R.id.signout);
        user_id = firebaseAuth.getCurrentUser().getUid();
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String data[]={"Edit Profile","My Orders","Edit Password"};
        int image[]={R.drawable.edit,R.drawable.order1,R.drawable.password};
        recyclerView.setAdapter(new MyAdapter(data,image,getContext()));


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new SpotsDialog.Builder()
                        .setContext(getActivity())
                        .setMessage("Signing out")
                        .setCancelable(false)
                        .setTheme(R.style.Custom)
                        .build();

                        alertDialog.show();

                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        Looper.prepare();

                        String a="";

                        Map<String,Object> tokenremove=new HashMap<>();
                        tokenremove.put("token",a);

                        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(tokenremove).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                alertDialog.dismiss();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getActivity(), Main.class));
                                getActivity().finish();

                                Toast.makeText(getContext(),"Successfully Signed Out",Toast.LENGTH_SHORT).show();

                            }

                        });
                            }
                }, 2000);

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                show(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    private void show(DataSnapshot dataSnapshot) {

        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            get_details d = new get_details();
            d.setName(dataSnapshot1.child(user_id).getValue(get_details.class).getName());
            d.setEmail(dataSnapshot1.child(user_id).getValue(get_details.class).getEmail());
            d.setImage(dataSnapshot1.child(user_id).getValue(get_details.class).getImage());
            user_name.setText(d.getName());
            user_email.setText(d.getEmail());
            String uri = d.getImage();
            Picasso.get().load(uri).fit().centerCrop().into(circleImageView);
        }


    }

}


