package com.example.textproject.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textproject.databinding.ItemBookmarkBinding;
import com.example.textproject.db.dto.BookmarkedCourse;
import com.example.textproject.listeners.BookmarkItemListener;

public class BookmarksAdapter extends ListAdapter<BookmarkedCourse, BookmarksAdapter.BookmarkViewHolder> {
    private final BookmarkItemListener listener;

    public BookmarksAdapter(BookmarkItemListener listener) {
        super(
                new DiffUtil.ItemCallback<>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull BookmarkedCourse oldItem, @NonNull BookmarkedCourse newItem) {
                        return oldItem.getBookmarkId() == newItem.getBookmarkId();
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull BookmarkedCourse oldItem, @NonNull BookmarkedCourse newItem) {
                        return true;
                    }
                }
        );
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookmarkBinding binding = ItemBookmarkBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new BookmarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        BookmarkedCourse bookmarkedCourse = getCurrentList().get(position);
        holder.bind(bookmarkedCourse, listener);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookmarkBinding binding;

        public BookmarkViewHolder(ItemBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BookmarkedCourse bookmarkedCourse, BookmarkItemListener listener) {
            binding.tvBookmarkCourseTitle.setText(bookmarkedCourse.getTitle());
            binding.btnDeleteBookmark.setOnClickListener(v ->
                    listener.onDeleteClickListener(
                            bookmarkedCourse.getBookmarkId(),
                            bookmarkedCourse.getTitle()
                    )
            );
        }
    }
}

