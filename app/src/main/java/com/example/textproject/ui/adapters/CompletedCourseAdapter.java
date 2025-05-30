package com.example.textproject.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textproject.databinding.ItemCompletedCourseBinding;
import com.example.textproject.db.entity.Course;
import com.example.textproject.listeners.ClickMyCourseListener;

import java.util.ArrayList;
import java.util.List;

public class CompletedCourseAdapter extends RecyclerView.Adapter<CompletedCourseAdapter.CompletedCourseViewHolder> {
    private List<Course> courseList;
    private final ClickMyCourseListener listener;

    public CompletedCourseAdapter(ClickMyCourseListener listener) {
        this.courseList = new ArrayList<>();
        this.listener = listener;
    }

    public void setCourses(List<Course> courses) {
        notifyItemRangeRemoved(0, courseList.size());
        courseList = courses;
        notifyItemRangeInserted(0, courseList.size());
    }

    @NonNull
    @Override
    public CompletedCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCompletedCourseBinding binding = ItemCompletedCourseBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new CompletedCourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedCourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.bind(course, listener);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CompletedCourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemCompletedCourseBinding binding;

        public CompletedCourseViewHolder(ItemCompletedCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Course course, ClickMyCourseListener listener) {
            binding.tvCompletedCourseTitle.setText(course.getTitle());
            binding.getRoot().setOnClickListener(view -> listener.onClickMyCourse(course.getCourseId()));
        }
    }
}
