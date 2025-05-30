package com.example.textproject.admin_ui.add_lesson;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.textproject.R;
import com.example.textproject.admin_ui.lessons.LessonsActivity;
import com.example.textproject.databinding.ActivityAddLessonBinding;
import com.example.textproject.db.dto.UserCourseDTO;
import com.example.textproject.db.entity.Lesson;
import com.example.textproject.utils.NotificationHelper;

public class AddLessonActivity extends AppCompatActivity {
    public static final String IS_UPDATE_KEY = "IS_UPDATE_KEY";
    private ActivityAddLessonBinding binding;
    private AddLessonActivityViewModel viewModel;
    public static final String LESSON_ID_KEY = "lessonId";
    private long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        viewModel = new ViewModelProvider(this).get(AddLessonActivityViewModel.class);

        long lessonId = getIntent().getLongExtra(LESSON_ID_KEY, -1);

        if (lessonId != -1) {
            viewModel.getLessonById(lessonId).observe(this, lesson -> {
                if (lesson != null) {
                    binding.etTitleLesson.setText(lesson.getTitle());
                    binding.etDescriptionLesson.setText(lesson.getDescription());
                    binding.etLessonLink.setText(lesson.getYoutubeLink());
                    binding.btnAddLesson.setText("حفظ");
                    courseId = lesson.getCourseId();
                }
            });
        } else {
            courseId = getIntent().getLongExtra(LessonsActivity.COURSE_ID_KEY, -1);
            if (courseId == -1) return;
        }

        binding.btnAddLesson.setOnClickListener(view -> {
            String lessonTitle = binding.etTitleLesson.getText().toString().trim();
            String lessonDescription = binding.etDescriptionLesson.getText().toString().trim();
            String lessonLink = binding.etLessonLink.getText().toString().trim();

            if (TextUtils.isEmpty(lessonTitle) || TextUtils.isEmpty(lessonDescription) || TextUtils.isEmpty(lessonLink)) {
                Toast.makeText(this, "يرجى إدخال جميع الحقول", Toast.LENGTH_SHORT).show();
                return;
            }

            if (lessonId != -1) {
                viewModel.updateLesson(
                        new Lesson(lessonId, courseId, lessonTitle, lessonDescription, lessonLink)
                );
            } else {
                viewModel.insertLesson(
                        new Lesson(courseId, lessonTitle, lessonDescription, lessonLink)
                );

                viewModel.getRegistrationsByCourseId(courseId, registrations -> {
                    for (UserCourseDTO registration : registrations) {
                        NotificationHelper.showCourseUpdateNotification(
                                getBaseContext(),
                                registration.getUserName(),
                                lessonTitle,
                                registration.getCourseTitle(),
                                registration.getUserId(),
                                courseId
                        );
                    }
                });
            }

            Toast.makeText(this, "تمت حفظ الدرس بنجاح", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
