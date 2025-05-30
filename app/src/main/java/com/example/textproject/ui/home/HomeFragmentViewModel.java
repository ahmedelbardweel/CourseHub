package com.example.textproject.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.textproject.db.repostory.MyRepository;
import com.example.textproject.listeners.GetCategoriesListener;

public class HomeFragmentViewModel extends AndroidViewModel {
    private final MyRepository repository;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    void getAllCategories(GetCategoriesListener listener) {
        repository.getAllCategories(listener);
    }


}
