package com.example.hardeep.myproject.admin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hardeep.myproject.admin.Add_order.Order_View_holder;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.admin.Add_order.add_order;
import com.example.hardeep.myproject.admin.details;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_all_orders extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton add, remove;
    FloatingActionMenu floatingActionMenu;
    DatabaseReference datareference;
    FirebaseRecyclerAdapter<details, Order_View_holder> firebaseRecyAdapter;
    String user_order_id;
    TextView search_text;
    Button search_button;

    public admin_all_orders() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_admin_all_orders, container, false);

        datareference= FirebaseDatabase.getInstance().getReference().child("2").child("Active Orders");

        add = v.findViewById(R.id.fabadd);
        remove = v.findViewById(R.id.fabremov);
        recyclerView = v.findViewById(R.id.ordersrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        search_text=v.findViewById(R.id.search_text_id);
        search_button=v.findViewById(R.id.search_button_id);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), add_order.class));
            }
        });



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyAdapter=new FirebaseRecyclerAdapter<details, Order_View_holder>(details.class,R.layout.format_order,Order_View_holder.class,datareference) {
            @Override
            protected void populateViewHolder(Order_View_holder viewHolder, details model, int position) {
                user_order_id=firebaseRecyAdapter.getRef(position).getKey();
                viewHolder.setName(model.getName_of_user());
                viewHolder.setUsername(model.getUsername_of_user());
                viewHolder.setOrderid("#"+user_order_id);


            }
        };
recyclerView.setAdapter(firebaseRecyAdapter);
    }
}


