package com.example.textproject.admin_ui.add_course;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.textproject.admin_ui.dialog.AddCategoryDialog;
import com.example.textproject.admin_ui.lessons.LessonsActivity;
import com.example.textproject.databinding.ActivityAddCourseBinding;
import com.example.textproject.db.entity.Category;
import com.example.textproject.db.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity implements AddCategoryDialog.AddCategoryListener {
    public static final String KEY_COURSE = "COURSE";
    public static final String IS_UPDATE_KEY = "isUpdate";
    private boolean isUpdate;
    private ActivityAddCourseBinding binding;
    private AddCourseActivityViewModel viewModel;
    private final List<Category> spinnerCategories = new ArrayList<>();
    private Long categories_id;
    private Long courseId;
    private Uri selectedImageUri;
    private ActivityResultLauncher<String[]> openDocumentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        openDocumentLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
            if (uri != null) {
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                selectedImageUri = uri;
                binding.courseImage.setImageURI(uri);
            }
        });

        binding.courseImage.setOnClickListener(view ->
                openDocumentLauncher.launch(new String[]{"image/*"})
        );

        viewModel = new ViewModelProvider(this).get(AddCourseActivityViewModel.class);

        isUpdate = getIntent().getBooleanExtra(IS_UPDATE_KEY, false);
        if (isUpdate) {
            binding.categoryLinearLayout.setVisibility(View.GONE);

            Course course = (Course) getIntent().getSerializableExtra(KEY_COURSE);
            if (course == null) return;
            setCourseData(course);
        } else {
            viewModel.getAllCategories(categories -> {
                spinnerCategories.addAll(categories);
                runOnUiThread(this::refreshSpinnerAdapter);
            });

            binding.btnAddCategory.setOnClickListener(view -> {
                AddCategoryDialog dialogWarning = new AddCategoryDialog(this);
                dialogWarning.show(getSupportFragmentManager(), "AddCategoryDialog");
            });
        }

        binding.btnGoToLessons.setOnClickListener(view -> {
            if (isUpdate) {
                startLessonsActivity();
            } else {
                if (categories_id != null) {
                    Course courseData = getCourse();
                    if (courseData != null) {
                        viewModel.insertCourse(courseData, id -> {
                            isUpdate = true;
                            courseId = id;
                            startLessonsActivity();
                        });
                    }
                } else {
                    Toast.makeText(this, "Please select a category or create a new category.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnAddCourse.setOnClickListener(view -> {
            if (courseId == null) {
                if (categories_id != null) {
                    Course courseData = getCourse();
                    if (courseData != null) {
                        viewModel.insertCourse(courseData, id -> courseId = id);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Please select a category or create a new category.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Course courseData = getCourse();
                if (courseData != null) {
                    courseData.setCourseId(courseId);
                    viewModel.updateCourse(courseData);
                    finish();
                }
            }
        });
    }

    private void startLessonsActivity() {
        Intent intent = new Intent(getBaseContext(), LessonsActivity.class);
        intent.putExtra(LessonsActivity.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    private void setCourseData(Course course) {
        binding.editTitle.setText(course.getTitle());
        binding.editDescription.setText(course.getDescription());
        binding.editHours.setText(String.valueOf(course.getHours()));
        binding.editPrice.setText(String.valueOf(course.getPrice()));
        binding.editTeacherName.setText(course.getTeacherName());

        selectedImageUri = Uri.parse(course.getCourseImage());
        binding.courseImage.setImageURI(selectedImageUri);

        courseId = course.getCourseId();
        categories_id = course.getCategoryId();
    }

    private Course getCourse() {
        String title = binding.editTitle.getText().toString().trim();
        String description = binding.editDescription.getText().toString().trim();
        String hours = binding.editHours.getText().toString().trim();
        String price = binding.editPrice.getText().toString().trim();
        String teacherName = binding.editTeacherName.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || hours.isEmpty() || price.isEmpty() || teacherName.isEmpty()) {
            Toast.makeText(this, "Please make sure to enter all data.", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please choose an image for the course.", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new Course(
                title,
                description,
                teacherName,
                Double.parseDouble(price),
                Integer.parseInt(hours),
                categories_id,
                selectedImageUri.toString()
        );
    }

    private void refreshSpinnerAdapter() {
        List<String> categories = new ArrayList<>();
        categories.add("Select the category");
        for (Category category : spinnerCategories) {
            categories.add(category.getName());
        }

        ArrayAdapter<String> adapter = getStringArrayAdapter(categories);
        binding.spinnerCategory.setAdapter(adapter);

        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    categories_id = spinnerCategories.get(i - 1).getCategoryId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter(List<String> categories) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories) {
                    @Override
                    public boolean isEnabled(int position) {
                        return position != 0;
                    }

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view;
                        if (position == 0) {
                            textView.setTextColor(Color.GRAY);
                        } else {
                            textView.setTextColor(Color.BLACK);
                        }
                        return view;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView textView = (TextView) view;
                        if (position == 0) {
                            textView.setTextColor(Color.GRAY);
                        } else {
                            textView.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public void onClickAddCategory(String name) {
        Category newCategory = new Category(name);
        viewModel.insertCategory(newCategory, id -> {
            newCategory.setCategoryId(id);
            spinnerCategories.add(newCategory);

            runOnUiThread(this::refreshSpinnerAdapter);
        });
    }
}
