package com.example.textproject.listeners;

import com.example.textproject.db.entity.Category;

import java.util.List;

public interface GetCategoriesListener {
    void onGetCategories(List<Category> categories);
}
