package com.example.hardeep.myproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RejectedOrders extends Fragment {

     private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView noorders;
    FirebaseRecyclerAdapter<details,Order_View_holder> firebaseRecyclerAdapter;
    RecyclerView recyclerView;
    DatabaseReference dataref;

    public RejectedOrders() {
        // Required empty public constructor
    }

      public static RejectedOrders newInstance(String param1, String param2) {
        RejectedOrders fragment = new RejectedOrders();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_rejected_orders, container, false);

        noorders=v.findViewById(R.id.noordersrej);
        recyclerView=v.findViewById(R.id.rejectedordersrecyclerview);
        dataref= FirebaseDatabase.getInstance().getReference().child("2").child("Rejected Orders");

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<details, Order_View_holder>
                (details.class,R.layout.format_order,Order_View_holder.class,dataref) {
            @Override
            protected void populateViewHolder(final Order_View_holder viewHolder, final details model, final int position) {

                if(firebaseRecyclerAdapter.getItemCount()<1)
                {
                    noorders.setVisibility(View.VISIBLE);
                }
                String uid=model.getUserid();
                viewHolder.setOrderid("#"+firebaseRecyclerAdapter.getRef(position).getKey());

                FirebaseDatabase.getInstance().getReference().child("2").child("Rejected Orders").child(firebaseRecyclerAdapter.getRef(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        viewHolder.setCost("INR "+dataSnapshot.child("totalamount").getValue(String.class));
                        viewHolder.setReason(dataSnapshot.child("reason").getValue(String.class));

                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i=new Intent(getActivity(),ViewRejected.class);
                                i.putExtra("orderid",firebaseRecyclerAdapter.getRef(position).getKey());
                                startActivity(i);
                            }
                        });
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

     public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
