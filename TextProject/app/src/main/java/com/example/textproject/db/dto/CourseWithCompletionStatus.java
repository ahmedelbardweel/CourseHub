package com.example.textproject.db.dto;

import androidx.room.Ignore;

public class CourseWithCompletionStatus {
    public long courseId;
    public String title;
    public String description;
    public int completionPercentage;

    public CourseWithCompletionStatus() {
    }

    @Ignore
    public CourseWithCompletionStatus(long courseId, String title, String description, int completionPercentage) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.completionPercentage = completionPercentage;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
