package com.example.textproject.ui.ongoing_courses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.textproject.db.dto.CourseWithCompletionStatus;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.utils.SharedPrefsUtils;

import java.util.List;

public class OngoingCoursesFragmentViewModel extends AndroidViewModel {
    LiveData<List<CourseWithCompletionStatus>> ongoingCourses;
    private final MyRepository repository;

    public OngoingCoursesFragmentViewModel(@NonNull Application application) {
        super(application);
        this.repository =  new MyRepository(application);

        long userId = new SharedPrefsUtils(application.getApplicationContext()).getUserId();
        ongoingCourses = repository.getOngoingMyCourses(userId);
    }
}
