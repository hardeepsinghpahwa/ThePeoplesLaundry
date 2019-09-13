package com.example.hardeep.myproject;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    int tabs;

    public ViewPagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs=tabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                CompletedOrders completedOrders = new CompletedOrders();
                return completedOrders;

            case 1:
                RejectedOrders rejectedOrders=new RejectedOrders();
                return rejectedOrders;


            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
