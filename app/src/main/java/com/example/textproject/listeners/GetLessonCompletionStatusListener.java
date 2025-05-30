package com.example.textproject.listeners;

import com.example.textproject.db.dto.LessonCompletionStatus;

import java.util.List;

public interface GetLessonCompletionStatusListener {
    void onGetLessonCompletionStatus(List<LessonCompletionStatus> list);
}
