package com.example.textproject.ui.course_lessons;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.textproject.db.entity.LessonCompletion;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.listeners.GetLessonCompletionStatusListener;
import com.example.textproject.listeners.GetRowIdListener;
import com.example.textproject.utils.SharedPrefsUtils;

public class CourseLessonsActivityViewModel extends AndroidViewModel {
    private final MyRepository repository;
    private final SharedPrefsUtils prefsUtils;

    public CourseLessonsActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new MyRepository(application);

        prefsUtils = new SharedPrefsUtils(application.getApplicationContext());
    }

    public void getRegistrationId(long courseId, GetRowIdListener listener) {
        repository.getRegistration(courseId, prefsUtils.getUserId(), listener);
    }

    public void getLessonsWithCompletionStatus(long courseId, GetLessonCompletionStatusListener listener) {
        repository.getLessonsWithCompletionStatus(courseId, prefsUtils.getUserId(), listener);
    }

    public void deleteLessonCompletion(long courseId, long lessonId) {
        repository.deleteLessonCompletion(prefsUtils.getUserId(), courseId, lessonId);
    }

    public void insertLessonCompletion(long lessonId, long courseId, long registrationId) {
        repository.insertLessonCompletion(new LessonCompletion(
                        lessonId, prefsUtils.getUserId(), courseId, registrationId
                )
        );
    }
}
