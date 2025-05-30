package com.example.textproject.admin_ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.textproject.db.entity.Course;
import com.example.textproject.db.repostory.MyRepository;

import java.util.List;

public class AdminHomeActivityViewModel extends AndroidViewModel {
    private final MyRepository repository;
    public LiveData<List<Course>> allCourses;

    public AdminHomeActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);

        getAllCourses();
    }


    void getAllCourses() {
        allCourses = repository.getAllCourses();
    }

    void deleteCourse(long courseId) {
        repository.deleteCourse(courseId);
    }
}