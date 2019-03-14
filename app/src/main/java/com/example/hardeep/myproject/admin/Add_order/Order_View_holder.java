package com.example.hardeep.myproject.admin.Add_order;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.admin.Show_order_details;

public class Order_View_holder extends RecyclerView.ViewHolder {

    View view;
    TextView username, name,orderid;

    public Order_View_holder(final View itemView) {
        super(itemView);
        view=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(itemView.getContext(), Show_order_details.class);
                i.putExtra("orderid",orderid.getText());
                itemView.getContext().startActivity(i);
            }
        });
    }

    public void setUsername(String Username) {
        username = view.findViewById(R.id.username_user);
        username.setText(Username);
    }

    public void setOrderid(String order_id) {
        orderid=view.findViewById(R.id.orderid_user);
        orderid.setText(order_id);
    }

    public void setName(String Name) {
        name = view.findViewById(R.id.name_user);
        name.setText(Name);


    }


}
