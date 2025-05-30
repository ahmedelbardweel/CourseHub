package com.example.textproject.ui.completed_courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.textproject.databinding.FragmentCompletedCoursesBinding;
import com.example.textproject.listeners.ClickMyCourseListener;
import com.example.textproject.ui.adapters.CompletedCourseAdapter;
import com.example.textproject.ui.course_lessons.CourseLessonsActivity;


public class CompletedCoursesFragment extends Fragment implements ClickMyCourseListener {
    private FragmentCompletedCoursesBinding binding;
    private CompletedCoursesFragmentViewModel viewModel;

    public CompletedCoursesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletedCoursesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CompletedCoursesFragmentViewModel.class);

        CompletedCourseAdapter adapter = new CompletedCourseAdapter(this);
        binding.rcCompletedCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcCompletedCourses.setAdapter(adapter);

        viewModel.completedCourses.observe(getViewLifecycleOwner(), courses -> {
            requireActivity().runOnUiThread(() -> {
                adapter.setCourses(courses);
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onClickMyCourse(long courseId) {
        Intent intent = new Intent(requireContext(), CourseLessonsActivity.class);
        intent.putExtra(CourseLessonsActivity.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }
}