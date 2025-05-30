package com.example.textproject.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.textproject.db.dto.CourseWithCompletionStatus;
import com.example.textproject.db.dto.LessonCompletionStatus;
import com.example.textproject.db.entity.Course;
import com.example.textproject.db.entity.LessonCompletion;

import java.util.List;

@Dao
public interface LessonCompletionDao {

    @Query("DELETE FROM LessonCompletion " +
            "WHERE userId = :userId AND courseId = :courseId AND lessonId = :lessonId")
    void deleteLessonCompletion(long userId, long courseId, long lessonId);

    @Query("SELECT Lesson.*, " +
            "CASE WHEN lc.lessonId IS NOT NULL THEN 1 ELSE 0 END AS isCompleted " +
            "FROM Lesson " +
            "LEFT JOIN LessonCompletion lc ON Lesson.lessonId = lc.lessonId AND lc.userId = :userId " +
            "WHERE Lesson.courseId = :courseId")
    List<LessonCompletionStatus> getLessonsForCourseWithCompletionStatus(long courseId, long userId);

    @Query("SELECT c.courseId, c.title, c.description, " +
            "CAST((SUM(CASE WHEN lc.lessonId IS NOT NULL THEN 1 ELSE 0 END) * 100.0) / COUNT(l.lessonId) AS INTEGER) AS completionPercentage " +
            "FROM Course c " +
            "LEFT JOIN Lesson l ON c.courseId = l.courseId " +
            "JOIN Registration r ON c.courseId = r.courseId " +
            "LEFT JOIN LessonCompletion lc ON l.lessonId = lc.lessonId AND lc.userId = :userId " +
            "WHERE r.userId = :userId " +
            "GROUP BY c.courseId " +
            "HAVING COUNT(l.lessonId) = 0 OR COUNT(l.lessonId) > SUM(CASE WHEN lc.lessonId IS NOT NULL THEN 1 ELSE 0 END)")
    LiveData<List<CourseWithCompletionStatus>> getOngoingMyCourses(long userId);

    @Query("SELECT c.* " +
            "FROM Course c " +
            "JOIN Registration r ON c.courseId = r.courseId " +
            "JOIN Lesson l ON c.courseId = l.courseId " +
            "LEFT JOIN LessonCompletion lc ON l.lessonId = lc.lessonId AND lc.userId = :userId " +
            "WHERE r.userId = :userId " +
            "GROUP BY c.courseId " +
            "HAVING COUNT(l.lessonId) = SUM(CASE WHEN lc.lessonId IS NOT NULL THEN 1 ELSE 0 END)")
    LiveData<List<Course>> getCompletedMyCourses(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLessonCompletion(LessonCompletion completion);


}
