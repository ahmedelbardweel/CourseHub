package com.example.textproject.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textproject.R;
import com.example.textproject.databinding.ItemScreenCoursesBinding;
import com.example.textproject.db.dto.CourseWithBookmark;
import com.example.textproject.db.entity.Course;
import com.example.textproject.listeners.ClickHomeCourseListener;

public class HomeCoursesAdapter extends ListAdapter<CourseWithBookmark, HomeCoursesAdapter.HomeCoursesViewHolder> {
    private static ClickHomeCourseListener listener;

    public HomeCoursesAdapter(ClickHomeCourseListener listener) {
        super(
                new DiffUtil.ItemCallback<>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull CourseWithBookmark oldItem, @NonNull CourseWithBookmark newItem) {
                        return oldItem.getCourse().getCourseId() == newItem.getCourse().getCourseId();
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull CourseWithBookmark oldItem, @NonNull CourseWithBookmark newItem) {
                        return oldItem.isBookmarked() == newItem.isBookmarked();
                    }
                }
        );
        HomeCoursesAdapter.listener = listener;
    }

    @NonNull
    @Override
    public HomeCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemScreenCoursesBinding binding = ItemScreenCoursesBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new HomeCoursesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCoursesViewHolder holder, int position) {
        Course course = getCurrentList().get(position).getCourse();
        boolean isBookmarked = getCurrentList().get(position).isBookmarked();
        Long bookmarkId = getCurrentList().get(position).getBookmarkId();

        holder.bind(course, isBookmarked, bookmarkId);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static class HomeCoursesViewHolder extends RecyclerView.ViewHolder {
        private final ItemScreenCoursesBinding binding;

        public HomeCoursesViewHolder(ItemScreenCoursesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Course course, boolean isBookmarked, Long bookmarkId) {
            binding.itemTitle.setText(course.getTitle());
            binding.itemTeacherName.setText(course.getTeacherName());
            binding.itemDescription.setText(course.getDescription());
            binding.courseImage.setImageURI(Uri.parse(course.getCourseImage()));

            if (isBookmarked) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark);
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border);
            }

            binding.btnBookmark.setOnClickListener(view -> {
                if (isBookmarked) {
                    listener.onDeleteBookmarked(bookmarkId, course.getTitle());
                } else {
                    listener.onInsertBookmarked(course.getCourseId(), course.getTitle());
                }
            });

            binding.getRoot().setOnClickListener(view -> listener.onClickCourse(course.getCourseId()));
        }
    }
}
