package com.example.textproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.textproject.db.entity.User;
import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.utils.SharedPrefsUtils;

public class HomeActivityViewModel extends AndroidViewModel {
    private final MyRepository repository;
    LiveData<User> currentUser;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);

        SharedPrefsUtils prefsUtils = new SharedPrefsUtils(application.getApplicationContext());
        getUserByUserIdLiveData(prefsUtils.getUserId());
    }

    public void getUserByUserIdLiveData(long userId) {
        currentUser = repository.getUserByUserIdLiveData(userId);
    }
}






