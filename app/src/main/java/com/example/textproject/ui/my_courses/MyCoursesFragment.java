package com.example.textproject.ui.my_courses;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.textproject.R;
import com.example.textproject.databinding.FragmentMyCoursesBinding;
import com.example.textproject.ui.adapters.MyCoursesStateAdapter;
import com.example.textproject.ui.completed_courses.CompletedCoursesFragment;
import com.example.textproject.ui.ongoing_courses.OngoingCoursesFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MyCoursesFragment extends Fragment {
    private FragmentMyCoursesBinding binding;

    public MyCoursesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyCoursesBinding.inflate(inflater, container, false);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new OngoingCoursesFragment());
        fragments.add(new CompletedCoursesFragment());

        MyCoursesStateAdapter adapter = new MyCoursesStateAdapter(this, fragments);
        binding.viewPagerMyCourses.setAdapter(adapter);

        String[] tabTitles = {"Ongoing", "Completed"};

        new TabLayoutMediator(binding.tabMyCourses, binding.viewPagerMyCourses, (tab, position) -> {
            Context context = requireContext();
            TextView textView = new TextView(context);

            textView.setText(tabTitles[position]);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(32, 12, 32, 12);
            textView.setTextSize(14);
            textView.setGravity(Gravity.CENTER);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.END);

            tab.setCustomView(textView);
        }).attach();

        TabLayout.Tab firstTab = binding.tabMyCourses.getTabAt(0);
        if (firstTab != null && firstTab.getCustomView() != null) {
            firstTab.getCustomView().setBackgroundResource(R.drawable.edit_tex);
        }

        binding.tabMyCourses.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    view.setBackgroundResource(R.drawable.edit_tex);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    view.setBackgroundResource(android.R.color.transparent);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
