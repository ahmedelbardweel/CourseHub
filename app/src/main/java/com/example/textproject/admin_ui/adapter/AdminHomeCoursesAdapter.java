package com.example.textproject.admin_ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textproject.databinding.ItemScreenAdminBinding;
import com.example.textproject.db.entity.Course;
import com.example.textproject.listeners.AdminCourseItemListener;

public class AdminHomeCoursesAdapter extends ListAdapter<Course, AdminHomeCoursesAdapter.AdminHomeCoursesViewHolder> {
    private final AdminCourseItemListener listener;

    public AdminHomeCoursesAdapter(AdminCourseItemListener listener) {
        super(
                new DiffUtil.ItemCallback<>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
                        return oldItem.getCourseId() == newItem.getCourseId();
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
                        return oldItem.getTitle().equals(newItem.getTitle())
                                && oldItem.getDescription().equals(newItem.getDescription())
                                && oldItem.getHours() == newItem.getHours()
                                && oldItem.getPrice() == newItem.getPrice()
                                && oldItem.getTeacherName().equals(newItem.getTeacherName())
                                && oldItem.getCourseImage().equals(newItem.getCourseImage());
                    }
                }
        );
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminHomeCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemScreenAdminBinding binding = ItemScreenAdminBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminHomeCoursesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHomeCoursesViewHolder holder, int position) {
        Course course = getCurrentList().get(position);
        holder.bind(course, listener);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static class AdminHomeCoursesViewHolder extends RecyclerView.ViewHolder {
        private final ItemScreenAdminBinding binding;

        public AdminHomeCoursesViewHolder(ItemScreenAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Course course, AdminCourseItemListener listener) {
            binding.itemTitle.setText(course.getTitle());
            binding.teacherName.setText(course.getTeacherName());
            binding.itemDescription.setText(course.getDescription());
            binding.ivDelete.setOnClickListener(v -> listener.onDeleteClickListener(course.getCourseId()));
            binding.editImage.setOnClickListener(v -> listener.onUpdateClickListener(course));
        }
    }
}

