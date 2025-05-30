package com.example.textproject.admin_ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textproject.databinding.ItemAdminLessonBinding;
import com.example.textproject.db.entity.Lesson;
import com.example.textproject.listeners.AdminLessonItemListener;

public class LessonsAdapter extends ListAdapter<Lesson, LessonsAdapter.LessonsAdapterViewHolder> {
    private final AdminLessonItemListener listener;

    public LessonsAdapter(AdminLessonItemListener listener) {
        super(
                new DiffUtil.ItemCallback<>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull Lesson oldItem, @NonNull Lesson newItem) {
                        return oldItem.getLessonId() == newItem.getLessonId();
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull Lesson oldItem, @NonNull Lesson newItem) {
                        return oldItem.getTitle().equals(newItem.getTitle())
                                && oldItem.getDescription().equals(newItem.getDescription())
                                && oldItem.getYoutubeLink().equals(newItem.getYoutubeLink());
                    }
                }
        );
        this.listener = listener;
    }

    @NonNull
    @Override
    public LessonsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminLessonBinding binding = ItemAdminLessonBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new LessonsAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsAdapterViewHolder holder, int position) {
        Lesson lesson = getCurrentList().get(position);
        holder.bind(lesson, listener);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static class LessonsAdapterViewHolder extends RecyclerView.ViewHolder {
        private final ItemAdminLessonBinding binding;

        public LessonsAdapterViewHolder(ItemAdminLessonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Lesson lesson, AdminLessonItemListener listener) {
            binding.tvAdminLessonTitle.setText(lesson.getTitle());
            binding.tvAdminLessonDescription.setText(lesson.getDescription());
            binding.tvAdminLessonLink.setText(lesson.getYoutubeLink());

            binding.ivDelete.setOnClickListener(v -> listener.onDeleteClickListener(lesson.getLessonId()));
            binding.ivEdit.setOnClickListener(v -> listener.onUpdateClickListener(lesson));
        }
    }
}

