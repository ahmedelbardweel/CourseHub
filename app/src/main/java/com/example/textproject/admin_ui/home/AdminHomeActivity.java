package com.example.textproject.admin_ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.textproject.R;
import com.example.textproject.admin_ui.adapter.AdminHomeCoursesAdapter;
import com.example.textproject.admin_ui.add_course.AddCourseActivity;
import com.example.textproject.databinding.ActivityAdminHomeBinding;
import com.example.textproject.db.entity.Course;
import com.example.textproject.listeners.AdminCourseItemListener;
import com.example.textproject.ui.sign_in.SignInActivity;
import com.example.textproject.utils.SharedPrefsUtils;

public class AdminHomeActivity extends AppCompatActivity implements AdminCourseItemListener {
    private ActivityAdminHomeBinding binding;
    private AdminHomeActivityViewModel viewModel;
    private AdminHomeCoursesAdapter adapter;
    private SharedPrefsUtils prefsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.adminHomeToolbar);

        viewModel = new ViewModelProvider(this).get(AdminHomeActivityViewModel.class);
        prefsUtils = new SharedPrefsUtils(getBaseContext());

        binding.rcCourses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminHomeCoursesAdapter(this);
        binding.rcCourses.setAdapter(adapter);

        viewModel.allCourses.observe(this, courses ->
                runOnUiThread(() -> adapter.submitList(courses)));

        binding.floatingActionButton.setOnClickListener(v ->
                startActivity(new Intent(getBaseContext(), AddCourseActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.add(Menu.NONE, 1, Menu.NONE, "log out");
        logoutItem.setIcon(R.drawable.ic_logout);
        logoutItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 1) {
            prefsUtils.setIsAdmin(false);
            startActivity(new Intent(getBaseContext(), SignInActivity.class));
            finish();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteAlertDialog(long courseId) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this course permanently?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteCourse(courseId);
                    Toast.makeText(this, "The course has been successfully deleted.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onDeleteClickListener(long courseId) {
        showDeleteAlertDialog(courseId);
    }

    @Override
    public void onUpdateClickListener(Course course) {
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra(AddCourseActivity.IS_UPDATE_KEY, true);
        intent.putExtra(AddCourseActivity.KEY_COURSE, course);
        startActivity(intent);
    }
}