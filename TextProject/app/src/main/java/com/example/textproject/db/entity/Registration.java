package com.example.textproject.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Course.class,
                        parentColumns = "courseId",
                        childColumns = "courseId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = @Index("registrationId")
)
public class Registration {
    @PrimaryKey(autoGenerate = true)
    private long registrationId;
    private long userId;
    private long courseId;

    public Registration() {
    }

    @Ignore
    public Registration(long registrationId, long userId, long courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.registrationId = registrationId;
    }

    @Ignore
    public Registration(long userId, long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(long registrationId) {
        this.registrationId = registrationId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public void setMyCourse_id(long myCourse_id) {
        this.registrationId = myCourse_id;
    }
}
