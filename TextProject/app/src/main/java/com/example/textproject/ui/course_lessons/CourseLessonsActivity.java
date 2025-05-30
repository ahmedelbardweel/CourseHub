package com.example.textproject.ui.course_lessons;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.textproject.databinding.ActivityCourseLessonsBinding;
import com.example.textproject.db.entity.Lesson;
import com.example.textproject.listeners.ClickLessonListener;
import com.example.textproject.ui.adapters.LessonsWithCompletionStatusAdapter;
import com.example.textproject.ui.course_details.CourseDetailsActivity;
import com.example.textproject.ui.lesson_details.LessonDetailsActivity;
import com.example.textproject.utils.SharedPrefsUtils;

public class CourseLessonsActivity extends AppCompatActivity implements ClickLessonListener {
    private ActivityCourseLessonsBinding binding;
    private CourseLessonsActivityViewModel viewModel;

    public static final String COURSE_ID_KEY = "courseId";
    public static final String USER_ID_KEY = "userId";
    private long courseRegistrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCourseLessonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.courseLessonsToolbar);

        viewModel = new ViewModelProvider(this).get(CourseLessonsActivityViewModel.class);

        long userId = getIntent().getLongExtra(USER_ID_KEY, -1);
        if (userId != -1) {
            SharedPrefsUtils prefsUtils = new SharedPrefsUtils(getBaseContext());
            prefsUtils.setRemembered(true);
            prefsUtils.setIsAdmin(false);
            prefsUtils.setUserId(userId);
        }

        long courseId = getIntent().getLongExtra(COURSE_ID_KEY, -1);
        if (courseId == -1) return;

        LessonsWithCompletionStatusAdapter adapter = new LessonsWithCompletionStatusAdapter(this);
        binding.rcCourseLessons.setLayoutManager(new LinearLayoutManager(this));
        binding.rcCourseLessons.setAdapter(adapter);

        viewModel.getLessonsWithCompletionStatus(courseId, adapter::setLessonsList);
        viewModel.getRegistrationId(courseId, registrationId -> courseRegistrationId = registrationId);

        binding.imCourseDetails.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CourseDetailsActivity.class);
            intent.putExtra(CourseDetailsActivity.KEY_COURSE_ID, courseId);
            startActivity(intent);
        });
    }

    @Override
    public void onClickLesson(Lesson lesson) {
        Intent intent = new Intent(getBaseContext(), LessonDetailsActivity.class);
        intent.putExtra(LessonDetailsActivity.KEY_LESSON, lesson);
        startActivity(intent);

    }
    

    @Override
    public void onLessonCheckedChangeListener(long lessonId, long courseId, boolean isChecked) {
        if (isChecked) {
            viewModel.insertLessonCompletion(lessonId, courseId, courseRegistrationId);
        } else {
            viewModel.deleteLessonCompletion(courseId, lessonId);
        }
    }
}