package com.example.textproject.ui.course_details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.textproject.db.entity.Registration;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.listeners.GetCourseListener;

public class CourseDetailsViewModel extends AndroidViewModel {
    private final MyRepository repository;
    public LiveData<Registration> registrationLiveData;

    public CourseDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    void getRegistrationLV(long userId, long courseId) {
        registrationLiveData = repository.getRegistrationLV(userId, courseId);
    }

    void insertRegistration(long userId, long courseId) {
        repository.insertRegistration(userId, courseId);
    }

    void deleteRegistration(long userId, long courseId) {
        repository.deleteRegistration(userId, courseId);
    }

    void getCoursesById(long courseId, GetCourseListener listener) {
        repository.getCoursesById(courseId, listener);
    }
}