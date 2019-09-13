package com.example.hardeep.myproject.user.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.hardeep.myproject.R;


public class price_list extends Fragment {


    RelativeLayout relativeLayout;

    public price_list() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_price_list, container, false);

        relativeLayout=v.findViewById(R.id.pricel);

        relativeLayout.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.Landing)
                .duration(1000)
                .playOn(relativeLayout);


        return v;
    }
}
