package com.example.textproject.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseId",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("lessonId")
)
public class Lesson implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long lessonId;
    private long courseId;
    private String title;
    private String description;
    private String youtubeLink;

    public Lesson() {
    }

    @Ignore
    public Lesson(long lessonId, long courseId, String title, String description, String youtubeLink) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.youtubeLink = youtubeLink;
    }

    @Ignore
    public Lesson(long courseId, String title, String description, String youtubeLink) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.youtubeLink = youtubeLink;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
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

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
}
