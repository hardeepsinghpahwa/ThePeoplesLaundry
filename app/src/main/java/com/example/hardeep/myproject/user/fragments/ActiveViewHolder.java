package com.example.hardeep.myproject.user.fragments;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hardeep.myproject.R;

public class ActiveViewHolder extends RecyclerView.ViewHolder {

    TextView cost,status,orderid;
    public ActiveViewHolder(@NonNull View itemView) {
        super(itemView);

        cost=itemView.findViewById(R.id.cost_id);
        status=itemView.findViewById(R.id.status);
        orderid=itemView.findViewById(R.id.display_order_number);
    }

    public void setCost(String c) {
        cost.setText(c);
    }

    public void setStatus(String s) {
        status.setText(s);
    }

    public void setOrderid(String o) {
        orderid.setText(o);
    }
}
