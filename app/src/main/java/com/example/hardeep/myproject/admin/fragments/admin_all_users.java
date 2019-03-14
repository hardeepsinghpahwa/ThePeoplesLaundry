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
import com.example.hardeep.myproject.admin.display_user_details;
import com.example.hardeep.myproject.get_details;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin_all_users extends Fragment {
    private DatabaseReference dataref;
    private RecyclerView recyclerView;
    String user,u_name;
    LinearLayout linearLayout;
    FirebaseRecyclerAdapter<get_details, Admin_ViewHolder> firebaseRecyclerAdapter;

    public admin_all_users() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataref = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_all_users, container, false);
        // Inflate the layout for this fragment


        recyclerView = v.findViewById(R.id.recyview);
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
                        Intent i=new Intent(getActivity(),display_user_details.class);

                        i.putExtra("u_id",firebaseRecyclerAdapter.getRef(position).getKey());
                        startActivity(i);

                    }
                });


            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

}