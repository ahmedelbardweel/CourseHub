package com.example.textproject.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.textproject.db.dto.UserCourseDTO;
import com.example.textproject.db.entity.Registration;

import java.util.List;

@Dao
public interface RegistrationDao {
    @Query("INSERT INTO Registration VALUES(NULL,:userId,:courseId)")
    void insertRegistration(long userId, long courseId);

    @Query("DELETE FROM Registration WHERE userId = :userId AND courseId = :courseId")
    void deleteRegistration(long userId, long courseId);

    @Query("SELECT u.userId, u.name AS userName, c.title AS courseTitle FROM Registration r " +
            "JOIN User u ON r.userId = u.userId " +
            "JOIN Course c ON r.courseId = c.courseId " +
            "WHERE r.courseId = :courseId")
    List<UserCourseDTO> getRegistrationsByCourseId(long courseId);

    @Query("SELECT * FROM Registration WHERE userId = :userId AND courseId = :courseId")
    Registration getRegistration(long userId, long courseId);

    @Query("SELECT * FROM Registration WHERE userId = :userId AND courseId = :courseId")
    LiveData<Registration> getRegistrationLV(long userId, long courseId);
}
