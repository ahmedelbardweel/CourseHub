package com.example.textproject.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.textproject.db.dao.BookmarkDao;
import com.example.textproject.db.dao.CategoryDao;
import com.example.textproject.db.dao.CoursesDao;
import com.example.textproject.db.dao.LessonCompletionDao;
import com.example.textproject.db.dao.LessonsDao;
import com.example.textproject.db.dao.RegistrationDao;
import com.example.textproject.db.dao.UserDao;
import com.example.textproject.db.entity.Bookmark;
import com.example.textproject.db.entity.Category;
import com.example.textproject.db.entity.Course;
import com.example.textproject.db.entity.Lesson;
import com.example.textproject.db.entity.Registration;
import com.example.textproject.db.entity.User;
import com.example.textproject.db.entity.LessonCompletion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Bookmark.class, Category.class, Course.class, Lesson.class, Registration.class, LessonCompletion.class}, version = 6, exportSchema = false)
public abstract class CoursesDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract LessonCompletionDao lessonCompletionDao();

    public abstract LessonsDao lessonsDao();

    public abstract CoursesDao coursesDao();

    public abstract CategoryDao categoryDao();

    public abstract BookmarkDao bookmarksDao();

    public abstract RegistrationDao myCoursesDao();


    private static volatile CoursesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CoursesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CoursesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CoursesDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
