package com.example.hardeep.myproject.admin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.admin.Admin_ViewHolder;
import com.example.hardeep.myproject.get_details;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Firebase_web extends Fragment {

    private DatabaseReference dataref;
    private RecyclerView recyclerView;
    String user,u_name;
    LinearLayout linearLayout;
    FirebaseRecyclerAdapter<get_details, Admin_ViewHolder> firebaseRecyclerAdapter;

    public Firebase_web() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_firebase_web, container, false);

       recyclerView=v.findViewById(R.id.recyclerviewusers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataref = FirebaseDatabase.getInstance().getReference().child("1").child("User details");
        linearLayout=v.findViewById(R.id.item_format);

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<get_details, Admin_ViewHolder>
                (get_details.class, R.layout.all_users_recycler_view_format, Admin_ViewHolder.class, dataref) {

            @Override
            protected void populateViewHolder( Admin_ViewHolder viewHolder, final get_details model, final int position) {
                viewHolder.setImage(getActivity(), model.getImage());
                viewHolder.setName(model.getName());
                viewHolder.setUsername(model.getUsername());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getActivity(),Send_notification_to_user.class);

                        i.putExtra("u_id",firebaseRecyclerAdapter.getRef(position).getKey());
                        startActivity(i);

                    }
                });


            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }


}
