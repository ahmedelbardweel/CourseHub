package com.example.textproject.db.dto;

public class BookmarkedCourse {
    private long bookmarkId;
    private String title;

    public BookmarkedCourse(long bookmarkId, String title) {
        this.bookmarkId = bookmarkId;
        this.title = title;
    }

    public long getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
