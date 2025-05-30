package com.example.textproject.ui.home;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.textproject.R;
import com.example.textproject.databinding.FragmentHomeBinding;
import com.example.textproject.db.entity.Category;
import com.example.textproject.ui.adapters.CategoryAdapter;
import com.example.textproject.ui.category.CategoryFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        HomeFragmentViewModel viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

        viewModel.getAllCategories(categories -> {
            ArrayList<CategoryFragment> fragments = new ArrayList<>();
            for (Category category : categories) {
                fragments.add(CategoryFragment.newInstance(category.getCategoryId()));
            }

            HomeFragment.this.requireActivity().runOnUiThread(() -> {
                CategoryAdapter adapter = new CategoryAdapter(this, fragments);
                binding.viewPager2.setAdapter(adapter);

                new TabLayoutMediator(binding.tabLayout, binding.viewPager2, (tab, position) -> {
                    Context context = requireContext();
                    TextView textView = new TextView(context);

                    textView.setText(categories.get(position).getName());
                    textView.setTextColor(Color.BLACK);
                    textView.setPadding(32, 12, 32, 12);
                    textView.setTextSize(14);
                    textView.setGravity(Gravity.CENTER);
                    textView.setSingleLine(true);
                    textView.setEllipsize(TextUtils.TruncateAt.END);

                    tab.setCustomView(textView);
                }).attach();

                TabLayout.Tab firstTab = binding.tabLayout.getTabAt(0);
                if (firstTab != null && firstTab.getCustomView() != null) {
                    firstTab.getCustomView().setBackgroundResource(R.drawable.tab_text_background);
                }

                binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
