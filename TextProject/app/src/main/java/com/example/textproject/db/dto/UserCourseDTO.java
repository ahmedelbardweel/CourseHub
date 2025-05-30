package com.example.textproject.db.dto;

public class UserCourseDTO {
    private long userId;
    private String userName;
    private String courseTitle;

    public UserCourseDTO(long userId, String userName, String courseTitle) {
        this.userId = userId;
        this.userName = userName;
        this.courseTitle = courseTitle;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
