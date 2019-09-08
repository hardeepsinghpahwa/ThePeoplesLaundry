package com.example.hardeep.myproject.user;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hardeep.myproject.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewholder> {

    public interface OnItemClickListener {
        void onItemClick(View item);
    }

    private String[]data;
    private int[] image;
    private Context context;
    int lastPosition=-1;


    public MyAdapter(String[]data,int []image,Context context)
    {
        this.data=data;
        this.context=context;
        this.image=image;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull final viewholder holder) {
        super.onViewAttachedToWindow(holder);
        holder.itemView.setVisibility(View.INVISIBLE);

        if (holder.getPosition() > lastPosition) {
            holder.itemView.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.itemView.setVisibility(View.VISIBLE);
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0f, 1f);
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0f, 1f);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.play(alpha).with(scaleY).with(scaleX);
                    animSet.setInterpolator(new OvershootInterpolator());
                    animSet.setDuration(400);
                    animSet.start();

                }
            }, 200);

            lastPosition = holder.getPosition();
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.format_recy_view_item_settings,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, final int position) {
    final String title= data[position];
    int icon=image[position];
    holder.textView.setText(title);
    holder.imageView.setImageDrawable(context.getResources().getDrawable(icon));


    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        Context c;
        TextView textView1;


        public viewholder(View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.image1);
            textView= itemView.findViewById(R.id.text1);
            itemView.isClickable();
            itemView.setOnClickListener(this);
            c=itemView.getContext();
        }

        @Override
        public void onClick(View view) {
             Intent intent;
            switch (getAdapterPosition()){
                case 0:
                    intent=new Intent(c,editprofile.class);
                    break;

                case 1:
                    intent=new Intent(c,myorders.class);
                    break;

                case 2:
                    intent=new Intent(c,editpassword.class);
                    break;
               default:
                   intent=null;

            }
            ActivityOptions options =
                    ActivityOptions.makeCustomAnimation(c, R.anim.fromright, R.anim.toright);
            c.startActivity(intent,options.toBundle());
        }
    }
}

