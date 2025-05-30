package com.example.textproject.admin_ui.add_lesson;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.textproject.db.entity.Lesson;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.listeners.GetRegistrationsListener;

public class AddLessonActivityViewModel extends AndroidViewModel {
    private final MyRepository repository;

    public AddLessonActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    void updateLesson(Lesson lesson) {
        repository.updateLesson(lesson);
    }

    void insertLesson(Lesson lesson) {
        repository.insertLesson(lesson);
    }

    public LiveData<Lesson> getLessonById(long lessonId) {
        return repository.getLessonById(lessonId);
    }

    public void getRegistrationsByCourseId(long courseId, GetRegistrationsListener listener) {
        repository.getRegistrationsByCourseId(courseId, listener);
    }
}