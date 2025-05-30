package com.example.textproject.db.repostory;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.textproject.db.CoursesDatabase;
import com.example.textproject.db.dao.BookmarkDao;
import com.example.textproject.db.dao.CategoryDao;
import com.example.textproject.db.dao.CoursesDao;
import com.example.textproject.db.dao.LessonCompletionDao;
import com.example.textproject.db.dao.LessonsDao;
import com.example.textproject.db.dao.RegistrationDao;
import com.example.textproject.db.dao.UserDao;
import com.example.textproject.db.dto.BookmarkedCourse;
import com.example.textproject.db.dto.CourseWithBookmark;
import com.example.textproject.db.dto.CourseWithCompletionStatus;
import com.example.textproject.db.entity.Bookmark;
import com.example.textproject.db.entity.Category;
import com.example.textproject.db.entity.Course;
import com.example.textproject.db.entity.Lesson;
import com.example.textproject.db.entity.LessonCompletion;
import com.example.textproject.db.entity.Registration;
import com.example.textproject.db.entity.User;
import com.example.textproject.listeners.GetCategoriesListener;
import com.example.textproject.listeners.GetCourseListener;
import com.example.textproject.listeners.GetLessonCompletionStatusListener;
import com.example.textproject.listeners.GetRegistrationsListener;
import com.example.textproject.listeners.GetRowIdListener;
import com.example.textproject.listeners.GetUserListener;

import java.util.List;

public class MyRepository {
    UserDao userDao;
    RegistrationDao registrationDao;
    LessonsDao lessonsDao;
    CoursesDao coursesDao;
    CategoryDao categoryDao;
    BookmarkDao dao;
    LessonCompletionDao lessonCompletionDao;

    public MyRepository(Application application) {
        CoursesDatabase database = CoursesDatabase.getDatabase(application);
        userDao = database.userDao();
        registrationDao = database.myCoursesDao();
        lessonsDao = database.lessonsDao();
        categoryDao = database.categoryDao();
        coursesDao = database.coursesDao();
        dao = database.bookmarksDao();
        lessonCompletionDao = database.lessonCompletionDao();
    }

    public void insertLessonCompletion(LessonCompletion lessonCompletion) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> {
            Lesson lesson = lessonsDao.getLessonByIdSync(lessonCompletion.getLessonId());
            Course course = coursesDao.getCourseByIdSync(lessonCompletion.getCourseId());
            User user = userDao.getUserByUserIdSync(lessonCompletion.getUserId());

            if (lesson == null || course == null || user == null) {
                Log.e("InsertLessonCompletion", "FOREIGN KEY VIOLATION: "
                        + "lesson=" + (lesson != null) + ", course=" + (course != null) + ", user=" + (user != null));
                return;
            }

            lessonCompletionDao.insertLessonCompletion(lessonCompletion);
        });
    }



    public void deleteLessonCompletion(long userId, long courseId, long lessonId) {
        CoursesDatabase.databaseWriteExecutor.execute(() ->
                lessonCompletionDao.deleteLessonCompletion(userId, courseId, lessonId));
    }

    public LiveData<Lesson> getLessonById(long lessonId) {
        return lessonsDao.getLessonById(lessonId);
    }

    public void getLessonsWithCompletionStatus(long courseId, long userId, GetLessonCompletionStatusListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() ->
                listener.onGetLessonCompletionStatus(lessonCompletionDao.getLessonsForCourseWithCompletionStatus(courseId, userId)));
    }

    public LiveData<List<CourseWithCompletionStatus>> getOngoingMyCourses(long userId) {
        return lessonCompletionDao.getOngoingMyCourses(userId);
    }

    public LiveData<List<Course>> getCompletedMyCourses(long userId) {
        return lessonCompletionDao.getCompletedMyCourses(userId);
    }

    public void insertUser(User user, GetRowIdListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> listener.onGetRowId(userDao.insertUser(user)));
    }

    public void updateUser(long userId, String name, String email, String password) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> userDao.updateUser(userId, name, email, password));
    }


    public void insertRegistration(long userId, long courseId) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> registrationDao.insertRegistration(userId, courseId));
    }

    public void deleteRegistration(long userId, long courseId) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> registrationDao.deleteRegistration(userId, courseId));
    }

    public void getRegistration(long userId, long courseId, GetRowIdListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> {
            Registration registration = registrationDao.getRegistration(userId, courseId);
            if (registration != null) {
                listener.onGetRowId(registration.getRegistrationId());
            } else {
                listener.onGetRowId(-1L);
            }
        });
    }


    public void getRegistrationsByCourseId(long courseId, GetRegistrationsListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() ->
                listener.onGetUserCourseRegistration(registrationDao.getRegistrationsByCourseId(courseId))
        );
    }

    public LiveData<Registration> getRegistrationLV(long userId, long course_id) {
        return registrationDao.getRegistrationLV(userId, course_id);
    }

    public LiveData<List<Lesson>> getLessonsByCourseId(long courseId) {
        return lessonsDao.getLessonsByCourseId(courseId);
    }

    public void insertLesson(Lesson lesson) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> lessonsDao.insertLesson(lesson));
    }

    public void updateLesson(Lesson lesson) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> lessonsDao.UpdateLesson(lesson));

    }

    public void deleteLesson(long lessonId) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> lessonsDao.deleteLesson(lessonId));

    }

    public void insertCourse(Course course, GetRowIdListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> {
            listener.onGetRowId(coursesDao.insertCourse(course));
        });
    }

    public void updateCourse(Course course) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> coursesDao.updateCourse(course));
    }

    public void deleteCourse(long courseId) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> coursesDao.deleteCourse(courseId));
    }

    public void insertCategory(Category category, GetRowIdListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> listener.onGetRowId(categoryDao.insertCategory(category)));
    }

    public void insertBookmark(Bookmark bookmark) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> dao.insertBookmark(bookmark));
    }

    public void deleteBookmark(long bookmarkId) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> dao.deleteBookmark(bookmarkId));
    }

    public LiveData<List<BookmarkedCourse>> getAllBookmarksByUserId(long userId) {
        return dao.getUserBookmarks(userId);
    }

    LiveData<List<User>> getAllUser() {
        return userDao.getAllUser();

    }

    public void getUserByUserId(long userId, GetUserListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() ->
                listener.onGetUser(userDao.getUserByUserId(userId)));
    }

    public LiveData<User> getUserByUserIdLiveData(long userId) {
        return userDao.getUserByUserIdLiveData(userId);
    }


    public void getUserByEmailAndPassword(String email, String password, GetUserListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() ->
                listener.onGetUser(userDao.getUserByEmailAndPassword(email, password)));
    }

    public void getCoursesById(long courseId, GetCourseListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> listener.onGetCourse(coursesDao.getCourseById(courseId)));
    }

    public LiveData<List<Course>> getAllCourses() {
        return coursesDao.getAllCourses();
    }

    public LiveData<List<CourseWithBookmark>> getAllByCategory(long userId, long categoryId) {
        return coursesDao.getAllByCategory(userId, categoryId);
    }

    public void getAllCategories(GetCategoriesListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> listener.onGetCategories(categoryDao.getAllCategories()));
    }

    public void getUserByEmail(String email, GetUserListener listener) {
        CoursesDatabase.databaseWriteExecutor.execute(() -> listener.onGetUser(userDao.getUserByEmail(email)));
    }
}
