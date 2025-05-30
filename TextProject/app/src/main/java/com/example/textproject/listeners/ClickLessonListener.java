package com.example.textproject.listeners;

import com.example.textproject.db.entity.Course;
import com.example.textproject.db.entity.Lesson;

public interface ClickLessonListener {
    void onClickLesson(Lesson lesson);
    void onLessonCheckedChangeListener(long lessonId, long courseId, boolean isChecked);
}
