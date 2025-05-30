package com.example.textproject.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.textproject.databinding.FragmentCategoryBinding;
import com.example.textproject.listeners.ClickHomeCourseListener;
import com.example.textproject.ui.adapters.HomeCoursesAdapter;
import com.example.textproject.ui.course_details.CourseDetailsActivity;
import com.google.android.material.snackbar.Snackbar;

public class CategoryFragment extends Fragment implements ClickHomeCourseListener {
    private FragmentCategoryBinding binding;
    private CategoryFragmentViewModel viewModel;

    private static final String ARG_CATEGORY_ID = "category_id";
    private long categoryId;


    public CategoryFragment() {
    }

    public static CategoryFragment newInstance(long categoryIdParam) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CATEGORY_ID, categoryIdParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getLong(ARG_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CategoryFragmentViewModel.class);

        binding.reCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        HomeCoursesAdapter homeCoursesAdapter = new HomeCoursesAdapter(CategoryFragment.this);
        binding.reCourses.setAdapter(homeCoursesAdapter);

        viewModel.getAllByCategory(categoryId);
        viewModel.allCourses.observe(getViewLifecycleOwner(), homeCoursesAdapter::submitList);

        return binding.getRoot();
    }

    @Override
    public void onClickCourse(long courseId) {
        Intent intent = new Intent(requireContext(), CourseDetailsActivity.class);
        intent.putExtra(CourseDetailsActivity.KEY_COURSE_ID, courseId);
        startActivity(intent);
    }

    @Override
    public void onInsertBookmarked(long courseId, String courseTitle) {
        viewModel.insertBookmark(courseId);
        String message = "تم اضافة " + courseTitle + " الى قائمة الكورسات المحفوظة";
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    private final Handler handler =new Handler(Looper.getMainLooper());
    private Runnable deleteAction;

    @Override
    public void onDeleteBookmarked(Long bookmarkId, String courseTitle) {
        deleteAction = () -> viewModel.deleteBookmark(bookmarkId);
        handler.postDelayed(deleteAction, 3000);

        String message = "تم ازالة " + courseTitle + " من قائمة الكورسات المحفوظة";
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setAction("تراجع", v -> handler.removeCallbacks(deleteAction))
                .show();
    }
}