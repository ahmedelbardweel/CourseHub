package com.example.textproject.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.textproject.db.entity.User;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.listeners.GetUserListener;
import com.example.textproject.utils.SharedPrefsUtils;

public class ProfileFragmentViewModel extends AndroidViewModel {
    private final MyRepository repository;

    public ProfileFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);


    }

    public void getUserByUserId(long userId, GetUserListener listener) {
        repository.getUserByUserId(userId, listener);
    }

    public void updateUser(long userId, String name, String email, String password) {
        repository.updateUser(userId, name, email, password);
    }
}






