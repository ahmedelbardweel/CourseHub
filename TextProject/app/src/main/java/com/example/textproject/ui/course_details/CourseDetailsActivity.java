package com.example.textproject.ui.course_details;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.textproject.databinding.ActivityCourseDetailsBinding;
import com.example.textproject.utils.SharedPrefsUtils;
import com.google.android.material.snackbar.Snackbar;

public class CourseDetailsActivity extends AppCompatActivity {
    private CourseDetailsViewModel viewModel;
    private SharedPrefsUtils prefsUtils;
    public static String KEY_COURSE_ID = "courseId";
    private boolean isRegistration = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityCourseDetailsBinding binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CourseDetailsViewModel.class);
        prefsUtils = new SharedPrefsUtils(getBaseContext());

        long courseId = getIntent().getLongExtra(KEY_COURSE_ID, -1);
        if (courseId == -1) return;

        viewModel.getRegistrationLV(prefsUtils.getUserId(), courseId);

        viewModel.registrationLiveData.observe(this, registration -> {
            if (registration == null) {
                isRegistration = false;
                binding.btnRegistrationInCourse.setText("subscription");
            } else {
                isRegistration = true;
                binding.btnRegistrationInCourse.setText("leaving");
            }
        });

        viewModel.getCoursesById(courseId, course -> {
            binding.tvCourseTitle.setText(course.getTitle());
            binding.tvHours.setText(String.valueOf(course.getHours()) + " Hours");
            binding.tvCoursePrice.setText(String.valueOf(course.getPrice()) + "$");
            binding.tvDescription.setText(course.getDescription());
//            binding.tvStudentCount.setText(String.valueOf(course.getStudentCount()));
            binding.tvTeacherName.setText(course.getTeacherName());
            binding.imageView.setImageURI(Uri.parse(course.getCourseImage()));

            binding.btnRegistrationInCourse.setOnClickListener(view -> {
                if (isRegistration) {
                    viewModel.deleteRegistration(prefsUtils.getUserId(), course.getCourseId());
                    Snackbar.make(binding.getRoot(), "Departure completed successfully", Snackbar.LENGTH_SHORT).show();
                } else {
                    viewModel.insertRegistration(prefsUtils.getUserId(), course.getCourseId());
                    Snackbar.make(binding.getRoot(), "You have successfully subscribed", Snackbar.LENGTH_SHORT).show();
                }
            });
        });
    }
}