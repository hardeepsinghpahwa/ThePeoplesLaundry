package com.example.hardeep.myproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.hardeep.myproject.user.order_items;

import java.util.List;

/**
 * Created by Hardeep on 15-03-2018.
 */

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

    Context context;
    List<order_items> items;

    public AdapterClass(Context context, List<order_items> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_list_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_name.setText(items.get(position).getItem_name());
        holder.price.setText(items.get(position).getPrice());
        holder.quantity.setText(items.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView price,quantity,item_name;
        public ViewHolder(View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.item_price);
            item_name=itemView.findViewById(R.id.itemname);
            quantity=itemView.findViewById(R.id.quan);

        }
    }
}
