package com.example.textproject.admin_ui.lessons;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.textproject.admin_ui.adapter.LessonsAdapter;
import com.example.textproject.admin_ui.add_lesson.AddLessonActivity;
import com.example.textproject.databinding.ActivityLessonsBinding;
import com.example.textproject.db.entity.Lesson;
import com.example.textproject.listeners.AdminLessonItemListener;

public class LessonsActivity extends AppCompatActivity implements AdminLessonItemListener {
    private ActivityLessonsBinding binding;
    private LessonsActivityViewModel viewModel;
    public static final String COURSE_ID_KEY = "COURSE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLessonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.lessonToolbar);

        viewModel = new ViewModelProvider(this).get(LessonsActivityViewModel.class);

        long courseId = getIntent().getLongExtra(COURSE_ID_KEY, -1);
        if (courseId == -1) return;

        viewModel.getLessonsByCourseId(courseId);

        binding.reLessons.setLayoutManager(new LinearLayoutManager(this));
        LessonsAdapter adapter = new LessonsAdapter(this);
        binding.reLessons.setAdapter(adapter);

        viewModel.lessonsLiveData.observe(this,
                lessons -> runOnUiThread(()
                        -> adapter.submitList(lessons)));

        binding.fabAddLesson.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), AddLessonActivity.class);
            intent.putExtra(AddLessonActivity.IS_UPDATE_KEY, false);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        });
    }

    private void showDeleteAlertDialog(long lessonId) {
        new AlertDialog.Builder(this)
                .setTitle("تنبيه!")
                .setMessage("هل أنت متأكد أنك تريد حذف هذا الدرس نهائيًا؟")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("حذف", (dialog, which) -> {
                    viewModel.deleteLesson(lessonId);
                    Toast.makeText(this, "تم حذف الدرس بنجاح", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("إلغاء", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onDeleteClickListener(long lessonId) {
        showDeleteAlertDialog(lessonId);
    }

   @Override
   public void onUpdateClickListener(Lesson lesson) {
       Intent intent = new Intent(getBaseContext(), AddLessonActivity.class);
       intent.putExtra(AddLessonActivity.LESSON_ID_KEY, lesson.getLessonId());
       startActivity(intent);
   }
}