package com.example.textproject.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyCoursesStateAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> fragments;

    public MyCoursesStateAdapter(@NonNull Fragment fragment, ArrayList<Fragment> fragments) {
        super(fragment);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);

    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
