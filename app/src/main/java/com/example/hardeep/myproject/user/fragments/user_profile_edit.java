package com.example.hardeep.myproject.user.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.hardeep.myproject.Main;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.get_details;
import com.example.hardeep.myproject.user.MyAdapter;
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
    RelativeLayout relativeLayout;
    user_profile_edit user_profile_edit;

    public user_profile_edit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        circleImageView = v.findViewById(R.id.circleImage);
        user_name = v.findViewById(R.id.nameuser);
        user_email = v.findViewById(R.id.emailuser);
        signout=v.findViewById(R.id.signout);
        user_id = firebaseAuth.getCurrentUser().getUid();
        relativeLayout=v.findViewById(R.id.editlayout);
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String data[]={"Edit Profile","My Orders","Edit Password"};
        int image[]={R.drawable.edit,R.drawable.order1,R.drawable.password};
        recyclerView.setAdapter(new MyAdapter(data,image,getContext()));

        relativeLayout.setVisibility(View.VISIBLE);
        signout.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceInUp)
                .duration(1000)
                .playOn(signout);

        YoYo.with(Techniques.BounceInDown)
                .duration(1000)
                .playOn(relativeLayout);

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
                                getActivity().overridePendingTransition(R.anim.fromright,R.anim.toright);
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

            get_details d = new get_details();
            d.setName(dataSnapshot.child(user_id).getValue(get_details.class).getName());
            d.setEmail(dataSnapshot.child(user_id).getValue(get_details.class).getEmail());
            d.setImage(dataSnapshot.child(user_id).getValue(get_details.class).getImage());
            user_name.setText(d.getName());
            user_email.setText(d.getEmail());
            String uri = d.getImage();
            Picasso.get().load(uri).fit().centerCrop().into(circleImageView);


    }

}


