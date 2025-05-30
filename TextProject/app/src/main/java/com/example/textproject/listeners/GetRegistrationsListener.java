package com.example.textproject.listeners;

import com.example.textproject.db.dto.UserCourseDTO;

import java.util.List;

public interface GetRegistrationsListener {
    void onGetUserCourseRegistration(List<UserCourseDTO> registrations);
}
