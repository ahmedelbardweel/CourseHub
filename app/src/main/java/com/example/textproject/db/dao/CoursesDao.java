package com.example.textproject.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.textproject.db.dto.CourseWithBookmark;
import com.example.textproject.db.entity.Course;

import java.util.List;

@Dao
public interface CoursesDao {
    @Insert
    long insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("DELETE FROM Course WHERE courseId =:courseId")
    void deleteCourse(long courseId);

    @Query("SELECT * FROM Course")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM Course WHERE courseId = :courseId")
    Course getCourseById(long courseId);

    @Query("SELECT c.* ,b.bookmarkId , CASE WHEN b.bookmarkId IS NOT NULL THEN 1 ELSE 0 END AS isBookmarked " +
            "FROM Course c " +
            "LEFT JOIN Bookmark b ON c.courseId = b.courseId AND b.userId = :userId " +
            "WHERE categoryId = :categoryId")
    LiveData<List<CourseWithBookmark>> getAllByCategory(long userId, long categoryId);


    @Query("SELECT * FROM Course WHERE courseId = :courseId LIMIT 1")
    Course getCourseByIdSync(long courseId);

}
