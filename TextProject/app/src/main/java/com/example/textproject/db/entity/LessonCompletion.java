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
                ),
                @ForeignKey(entity = Lesson.class,
                        parentColumns = "lessonId",
                        childColumns = "lessonId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("lessonCompletionId")}
)

public class LessonCompletion {
    @PrimaryKey(autoGenerate = true)
    private long lessonCompletionId;
    private long lessonId;
    private long userId;
    private long courseId;

    private long registrationId;

    public LessonCompletion() {
    }

    @Ignore
    public LessonCompletion(long lessonCompletionId, long lessonId, long userId, long courseId, long registrationId) {
        this.lessonCompletionId = lessonCompletionId;
        this.lessonId = lessonId;
        this.userId = userId;
        this.courseId = courseId;
        this.registrationId = registrationId;
    }

    public LessonCompletion(long lessons_id, long userId, long courseId, long registrationId) {
        this.lessonId = lessons_id;
        this.userId = userId;
        this.courseId = courseId;
        this.registrationId = registrationId;
    }

    public long getLessonCompletionId() {
        return lessonCompletionId;
    }

    public void setLessonCompletionId(long lessonCompletionId) {
        this.lessonCompletionId = lessonCompletionId;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
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

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(long registrationId) {
        this.registrationId = registrationId;
    }
}
