package com.example.hardeep.myproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TheAdapterClass extends RecyclerView.Adapter<TheAdapterClass.TheViewHolderClass> {

    ArrayList<d> list=new ArrayList<>();

    public TheAdapterClass(ArrayList<d> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TheViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_order_format, null);
        return new TheViewHolderClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TheViewHolderClass theViewHolderClass, int i) {
        theViewHolderClass.amount.setText(list.get(i).getTotalamount());
        theViewHolderClass.status.setText(list.get(i).getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TheViewHolderClass extends RecyclerView.ViewHolder{

        TextView orderid,amount,status;

        public TheViewHolderClass(@NonNull View itemView) {
            super(itemView);

            orderid=itemView.findViewById(R.id.display_order_number);
            amount=itemView.findViewById(R.id.cost_id);
            status=itemView.findViewById(R.id.status);
        }
    }
}
