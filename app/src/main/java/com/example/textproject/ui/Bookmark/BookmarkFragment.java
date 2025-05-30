package com.example.textproject.ui.Bookmark;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.textproject.databinding.FragmentBookmarkBinding;
import com.example.textproject.listeners.BookmarkItemListener;
import com.example.textproject.ui.adapters.BookmarksAdapter;
import com.google.android.material.snackbar.Snackbar;

public class BookmarkFragment extends Fragment implements BookmarkItemListener {

    private FragmentBookmarkBinding binding;
    private BookMarksActivityViewModel viewModel;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable deleteAction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BookMarksActivityViewModel.class);

        BookmarksAdapter adapter = new BookmarksAdapter(this);
        binding.rycBookMark.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rycBookMark.setAdapter(adapter);

        viewModel.bookmarkedCourses.observe(getViewLifecycleOwner(), adapter::submitList);
    }

    @Override
    public void onDeleteClickListener(long bookmarkId, String courseTitle) {
        deleteAction = () -> viewModel.deleteBookmark(bookmarkId);
        handler.postDelayed(deleteAction, 5000);

        String message = "تم إزالة " + courseTitle + " من قائمة الكورسات المحفوظة";
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setAction("تراجع", v -> handler.removeCallbacks(deleteAction))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
