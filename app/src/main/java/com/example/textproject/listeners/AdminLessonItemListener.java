package com.example.textproject.listeners;

import com.example.textproject.db.entity.Course;
import com.example.textproject.db.entity.Lesson;

public interface AdminLessonItemListener {
    void onDeleteClickListener(long lessonId);

    void onUpdateClickListener(Lesson lesson);
}