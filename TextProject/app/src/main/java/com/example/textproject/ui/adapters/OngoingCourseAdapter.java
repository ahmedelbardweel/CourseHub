package com.example.textproject.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textproject.databinding.ItemOngoingCourseBinding;
import com.example.textproject.db.dto.CourseWithCompletionStatus;
import com.example.textproject.listeners.ClickMyCourseListener;

import java.util.ArrayList;
import java.util.List;

public class OngoingCourseAdapter extends RecyclerView.Adapter<OngoingCourseAdapter.OngoingCourseViewHolder> {
    private List<CourseWithCompletionStatus> courseList;
    private final ClickMyCourseListener listener;

    public OngoingCourseAdapter(ClickMyCourseListener listener) {
        this.courseList = new ArrayList<>();
        this.listener = listener;
    }

    public void setCourses(List<CourseWithCompletionStatus> courses) {
        notifyItemRangeRemoved(0, courseList.size());
        courseList = courses;
        notifyItemRangeInserted(0, courseList.size());
    }

    @NonNull
    @Override
    public OngoingCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOngoingCourseBinding binding = ItemOngoingCourseBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new OngoingCourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingCourseViewHolder holder, int position) {
        CourseWithCompletionStatus course = courseList.get(position);
        holder.bind(course, listener);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class OngoingCourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemOngoingCourseBinding binding;

        public OngoingCourseViewHolder(ItemOngoingCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(CourseWithCompletionStatus course, ClickMyCourseListener listener) {
            binding.tvOngoingCourseTitle.setText(course.getTitle());
            binding.progressText.setText(String.valueOf(course.getCompletionPercentage()));
            binding.circularProgressIndicator.setProgress(course.getCompletionPercentage());

            binding.getRoot().setOnClickListener(view -> listener.onClickMyCourse(course.courseId));
        }
    }

    private static final String TAG = "OngoingCourseAdapter";
}
