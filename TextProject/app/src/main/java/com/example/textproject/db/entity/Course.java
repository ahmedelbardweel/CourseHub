package com.example.textproject.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "categoryId",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("courseId")
)
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long courseId;
    private String title;
    private String description;
    private String teacherName;
    private double price;
    private int hours;
    private long categoryId;
    private String courseImage;


    @ColumnInfo(defaultValue = "0")
    private int studentCount;

    public Course() {
    }

    @Ignore

    public Course(String title, String description, String teacherName, double price, int hours, long categoryId,String courseImage) {
        this.title = title;
        this.description = description;
        this.teacherName = teacherName;
        this.price = price;
        this.hours = hours;
        this.categoryId = categoryId;
        this.courseImage = courseImage;
    }


    @Ignore

    public Course(long courseId, String title, String description, String teacherName, double price, int hours, long categoryId, int studentCount, String courseImage) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.teacherName = teacherName;
        this.price = price;
        this.hours = hours;
        this.categoryId = categoryId;
        this.studentCount = studentCount;
        this.courseImage = courseImage;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
}
