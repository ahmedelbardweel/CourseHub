package com.example.textproject.ui.Bookmark;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.textproject.db.dto.BookmarkedCourse;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.utils.SharedPrefsUtils;

import java.util.List;

public class BookMarksActivityViewModel extends AndroidViewModel {
    private final MyRepository repository;
    public LiveData<List<BookmarkedCourse>> bookmarkedCourses;

    public BookMarksActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);

        long userId = new SharedPrefsUtils(application.getBaseContext()).getUserId();
        getAllBookmarksByUserId(userId);
    }

    void getAllBookmarksByUserId(long userId) {
        bookmarkedCourses = repository.getAllBookmarksByUserId(userId);
    }

    public void deleteBookmark(long bookmark_id) {
        repository.deleteBookmark(bookmark_id);
    }
}
