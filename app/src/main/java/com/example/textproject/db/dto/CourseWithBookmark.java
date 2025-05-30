package com.example.textproject.db.dto;

import androidx.room.Embedded;

import com.example.textproject.db.entity.Course;

public class CourseWithBookmark {
    @Embedded
    private Course course;
    private Long bookmarkId;
    private boolean isBookmarked;

    public CourseWithBookmark(Course course, Long bookmarkId, boolean isBookmarked) {
        this.course = course;
        this.bookmarkId = bookmarkId;
        this.isBookmarked = isBookmarked;
    }

    public Long getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
