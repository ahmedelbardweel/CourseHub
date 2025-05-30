package com.example.textproject.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.textproject.db.entity.User;

import java.util.List;


@Dao
public interface UserDao {
    @Insert
    long insertUser(User user);

    @Query("UPDATE User SET name = :name, email = :email, password = :password WHERE userId = :userId")
    void updateUser(long userId, String name, String email, String password);

    @Delete
    void deleteUser(User user);

    @Query("select * from User")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM User WHERE userId =:userId")
    LiveData<User> getUserByUserIdLiveData(long userId);

    @Query("SELECT * FROM User WHERE userId =:userId")
    User getUserByUserId(long userId);

    @Query("SELECT * FROM User where email =:email And password=:password")
    User getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM User where email =:email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM User WHERE userId = :userId LIMIT 1")
    User getUserByUserIdSync(long userId);

}
