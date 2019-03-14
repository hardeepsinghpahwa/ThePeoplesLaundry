package com.example.hardeep.myproject.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hardeep.myproject.R;

import java.util.ArrayList;

/**
 * Created by Hardeep on 28-03-2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ArrayList<String> orderid;
    ArrayList<String> costs;
    Context context;

    public OrderAdapter(ArrayList<String> orderid,ArrayList<String >costs,Context context) {
        this.orderid=orderid;
        this.costs=costs;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_order_format,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.orderno.setText(orderid.get(position));
        holder.cost.setText(costs.get(position));


    }

    @Override
    public int getItemCount() {

        if(orderid.size()!=0)
            return orderid.size();
        else
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView orderno,cost;


        public ViewHolder(View itemView) {

            super(itemView);
            orderno=itemView.findViewById(R.id.display_order_number);
            cost=itemView.findViewById(R.id.cost_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(context, Order_click_details.class);
                    i.putExtra("ordernumber",orderno.getText());
                    context.startActivity(i);
                }
            });





        }
    }
}
