package com.example.hardeep.myproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class Order_View_holder extends RecyclerView.ViewHolder {

    View view;
    TextView reason, cost,orderid;

    public Order_View_holder(final View itemView) {
        super(itemView);
        view=itemView;

    }

    public void setReason(String r) {
        reason = view.findViewById(R.id.reaason);
        reason.setText(r);
    }

    public void setOrderid(String order_id) {
        orderid=view.findViewById(R.id.orderid_user);
        orderid.setText(order_id);
    }

    public void setCost(String c) {
        cost = view.findViewById(R.id.cost_user);
        cost.setText(c);


    }


}
