package com.example.textproject.ui.sign_in;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.textproject.HomeActivity;
import com.example.textproject.admin_ui.home.AdminHomeActivity;
import com.example.textproject.databinding.SigninActivityBinding;
import com.example.textproject.ui.sign_up.SignUpActivity;
import com.example.textproject.utils.SharedPrefsUtils;

import java.util.Locale;

public class SignInActivity extends AppCompatActivity {
    private SigninActivityBinding binding;
    private SignInActivityViewModel viewModel;
    private SharedPrefsUtils prefsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = SigninActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SignInActivityViewModel.class);
        prefsUtils = new SharedPrefsUtils(getBaseContext());

        if (prefsUtils.getRemembered()) {
            if (prefsUtils.getIsAdmin()) {
                startAdminHomeActivity();
            } else if (prefsUtils.getUserId() != -1) {
                startHomeActivity();
            }
        }

        binding.signUpTv.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        binding.signInBt.setOnClickListener(view -> {
            String email = binding.emailET.getText().toString().trim();
            String password = binding.passwordEt.getText().toString().trim();

            if (isAdmin(email, password)) {
                prefsUtils.setRemembered(binding.rememberMeCheckbox.isChecked());
                prefsUtils.setIsAdmin(true);
                startAdminHomeActivity();
            } else {
                checkUserInDatabase(email, password);
            }
        });
    }

    private void checkUserInDatabase(String email, String password) {
        viewModel.getUserByEmailAndPassword(email, password, user -> {
            if (user != null) {
                prefsUtils.setUserId(user.getUserId());
                prefsUtils.setRemembered(binding.rememberMeCheckbox.isChecked());
                startHomeActivity();
            } else {
                runOnUiThread(() ->
                        Toast.makeText(this, "Error in data", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private boolean isAdmin(String email, String password) {
        return email.equals("admin@gmail.com") && password.equals("admin");
    }

    private void startAdminHomeActivity() {
        Intent intent = new Intent(SignInActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startHomeActivity() {
        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    private Context updateBaseContextLocale(Context context) {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        return context.createConfigurationContext(configuration);
    }
}
