package com.example.textproject.ui.sign_in;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.listeners.GetUserListener;

public class SignInActivityViewModel extends AndroidViewModel {
    private final MyRepository repository;

    public SignInActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    void getUserByEmailAndPassword(String email, String password, GetUserListener listener) {
        repository.getUserByEmailAndPassword(email, password, listener);
    }
}