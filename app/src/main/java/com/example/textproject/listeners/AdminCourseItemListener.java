package com.example.textproject.listeners;

import com.example.textproject.db.entity.Course;

public interface AdminCourseItemListener {
    void onDeleteClickListener(long courseId);

    void onUpdateClickListener(Course course);
}