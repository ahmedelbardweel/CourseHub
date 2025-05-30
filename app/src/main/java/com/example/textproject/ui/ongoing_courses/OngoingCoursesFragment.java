package com.example.textproject.ui.ongoing_courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.textproject.ui.course_lessons.CourseLessonsActivity;
import com.example.textproject.databinding.FragmentOngoingCoursesBinding;
import com.example.textproject.listeners.ClickMyCourseListener;
import com.example.textproject.ui.adapters.OngoingCourseAdapter;


public class OngoingCoursesFragment extends Fragment implements ClickMyCourseListener {
    private FragmentOngoingCoursesBinding binding;
    private OngoingCoursesFragmentViewModel viewModel;

    public OngoingCoursesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOngoingCoursesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(OngoingCoursesFragmentViewModel.class);

        OngoingCourseAdapter adapter = new OngoingCourseAdapter( this);
        binding.rcOngoingCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcOngoingCourses.setAdapter(adapter);

        viewModel.ongoingCourses.observe(getViewLifecycleOwner(), courses -> {
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