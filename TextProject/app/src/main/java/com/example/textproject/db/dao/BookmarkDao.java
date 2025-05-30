package com.example.textproject.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.textproject.db.dto.BookmarkedCourse;
import com.example.textproject.db.entity.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert
    void insertBookmark(Bookmark bookmark);

    @Query("DELETE FROM Bookmark WHERE bookmarkId = :bookmarkId")
    void deleteBookmark(long bookmarkId);

    @Query("SELECT Bookmark.bookmarkId, Course.title FROM Bookmark INNER JOIN Course ON Bookmark.courseId = Course.courseId WHERE userId =:userId")
    LiveData<List<BookmarkedCourse>> getUserBookmarks(long userId);
}
