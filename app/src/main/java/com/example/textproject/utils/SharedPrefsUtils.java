package com.example.textproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtils {
    private static final String PREF_NAME = "MyPrefs";
    private final SharedPreferences sharedPreferences;

    public SharedPrefsUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setIsAdmin(boolean isAdmin) {
        sharedPreferences.edit().putBoolean("isAdmin", isAdmin).apply();
    }

    public boolean getIsAdmin() {
        return sharedPreferences.getBoolean("isAdmin", false);
    }

    public void setUserId(long userId) {
        sharedPreferences.edit().putLong("userId", userId).apply();
    }

    public long getUserId() {
        return sharedPreferences.getLong("userId", -1);
    }

    public void removeUserId() {
        sharedPreferences.edit().remove("userId").apply();
    }

    public void setRemembered(boolean remembered) {
        sharedPreferences.edit().putBoolean("remembered", remembered).apply();
    }

    public boolean getRemembered() {
        return sharedPreferences.getBoolean("remembered", false);
    }
}