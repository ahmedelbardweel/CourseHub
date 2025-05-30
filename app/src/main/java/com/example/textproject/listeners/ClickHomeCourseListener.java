package com.example.textproject.listeners;

public interface ClickHomeCourseListener {
    void onClickCourse(long courseId);

    void onInsertBookmarked(long courseId,String courseTitle);

    void onDeleteBookmarked(Long bookmarkId,String courseTitle);
}
