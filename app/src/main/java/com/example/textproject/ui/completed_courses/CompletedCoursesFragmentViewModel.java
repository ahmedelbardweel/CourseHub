package com.example.textproject.ui.completed_courses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.textproject.db.dto.CourseWithCompletionStatus;
import com.example.textproject.db.entity.Course;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.utils.SharedPrefsUtils;

import java.util.List;

public class CompletedCoursesFragmentViewModel extends AndroidViewModel {
    LiveData<List<Course>> completedCourses;
    private final MyRepository repository;

    public CompletedCoursesFragmentViewModel(@NonNull Application application) {
        super(application);
        this.repository =  new MyRepository(application);

        long userId = new SharedPrefsUtils(application.getApplicationContext()).getUserId();
        completedCourses = repository.getCompletedMyCourses(userId);
    }
}
