package com.example.hardeep.myproject.admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hardeep.myproject.R;
import com.squareup.picasso.Picasso;

public class Admin_ViewHolder extends RecyclerView.ViewHolder{

    View v;
    TextView recname,recusername;
    ImageView recimage;
    public Admin_ViewHolder(View itemView) {
        super(itemView);
        v = itemView;

    }

    public void setName(String Name) {
        recname = v.findViewById(R.id.recname);
        recname.setText(Name);
    }

    public void setUsername(String Username) {
        recusername = v.findViewById(R.id.recusername);
        recusername.setText(Username);
    }

    public void setImage(Context context,String image)
    {
        recimage=v.findViewById(R.id.recimage);
        Picasso.with(context).load(image).into(recimage);
    }


}
