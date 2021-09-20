package com.example.edebisozler.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.edebisozler.fragment.FlowFragment;
import com.example.edebisozler.fragment.MenuFragment;

public class ViewpagerAdapter extends FragmentStateAdapter {

    private static final int CARD_ITEM_SIZE = 10 ;

    public ViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment selectedFragment;

        switch (position){
            case 0:
                selectedFragment = MenuFragment.newInstance();
                break;
            case 1:
                selectedFragment = FlowFragment.newInstance();
                break;
            default:
                return null;
        }

        return selectedFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }



}