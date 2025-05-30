package com.example.textproject.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.textproject.HomeActivity;
import com.example.textproject.databinding.ActivitySignUpBinding;
import com.example.textproject.db.entity.User;
import com.example.textproject.ui.sign_in.SignInActivity;
import com.example.textproject.utils.SharedPrefsUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpActivityViewModel viewModel;
    boolean isPasswordVisible = false;

    boolean isConfirmPasswordVisible = false;
    private SharedPrefsUtils prefsUtils;

    private ActivityResultLauncher<String> mGetContent;
    private byte[] userImageBlob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SignUpActivityViewModel.class);
        prefsUtils = new SharedPrefsUtils(getBaseContext());

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    if (inputStream != null) {
                        userImageBlob = getBytes(inputStream);
                        binding.ivUserImage.setImageURI(uri);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        binding.siginTv.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });


        binding.ivUserImage.setOnClickListener(view -> mGetContent.launch("image/*"));

        binding.signupBt.setOnClickListener(v -> {
            String name = binding.fullNameET.getText().toString().trim();
            String email = binding.emailET.getText().toString().trim();
            String password = Objects.requireNonNull(binding.passwordEt.getText()).toString().trim();
            String confirmPassword = Objects.requireNonNull(binding.confirmPasswordEt.getText()).toString().trim();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()
                    && !confirmPassword.isEmpty() && userImageBlob != null) {
                if (password.equals(confirmPassword)) {
                    viewModel.getUserByEmail(email, user -> {
                        if (user == null) {
                            viewModel.insertUser(new User(name, email, password, userImageBlob), id -> {
                                prefsUtils.setUserId(id);
                                startHomeActivity();
                            });
                        } else {
                            runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Email already exist", Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "Password and confirm password don’t match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignUpActivity.this, "fill All fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, bytesRead);
        }

        return byteBuffer.toByteArray();
    }

}