package com.example.hardeep.myproject.user;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hardeep.myproject.R;

import java.util.ArrayList;

/**
 * Created by Hardeep on 28-03-2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ArrayList<String> orderid;
    ArrayList<String> costs;
    ArrayList<String> statuses;
    Context context;
    ProgressBar progressBar;

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        progressBar.setVisibility(View.GONE);
    }

    public OrderAdapter(ArrayList<String> orderid, ArrayList<String >costs, ArrayList<String> statuses, Context context,ProgressBar progressBar) {
        this.orderid=orderid;
        this.costs=costs;
        this.statuses=statuses;
        this.context=context;
        this.progressBar=progressBar;
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
        holder.status.setText(statuses.get(position));
    }

    @Override
    public int getItemCount() {

        if(orderid.size()!=0)
            return orderid.size();
        else
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView orderno,cost,status;


        public ViewHolder(View itemView) {

            super(itemView);
            orderno=itemView.findViewById(R.id.display_order_number);
            cost=itemView.findViewById(R.id.cost_id);
            status=itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final StringBuilder s=new StringBuilder(orderno.getText());
                    s.deleteCharAt(0);
                    final String order=s.toString();
                    Intent i=new Intent(context,Order_click_details.class);
                    i.putExtra("ordernumber",order);
                    context.startActivity(i);
                }
            });
        }
    }
}
