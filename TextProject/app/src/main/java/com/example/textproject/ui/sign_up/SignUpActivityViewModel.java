package com.example.textproject.ui.sign_up;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.textproject.db.entity.User;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.listeners.GetRowIdListener;
import com.example.textproject.listeners.GetUserListener;

public class SignUpActivityViewModel extends AndroidViewModel {
    private final MyRepository repository;

    public SignUpActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    void getUserByEmail(String email, GetUserListener listener) {
        repository.getUserByEmail(email, listener);
    }

    void insertUser(User user, GetRowIdListener listener) {
        repository.insertUser(user, listener);
    }
}