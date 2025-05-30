package com.example.textproject.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.textproject.ui.course_lessons.CourseLessonsActivity;

import java.util.Random;

public class NotificationHelper {
    private static final String CHANNEL_ID = "course_updates_channel";
    private static final String CHANNEL_NAME = "Course Updates";

    public static void showCourseUpdateNotification(
            Context context,
            String userName,
            String lessonTitle,
            String courseTitle,
            long userId,
            long courseId
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        String notificationText = "لقد تم إضافة الدرس \"" + lessonTitle + "\" على كورس \"" + courseTitle;

        Intent intent = new Intent(context, CourseLessonsActivity.class);
        intent.putExtra(CourseLessonsActivity.USER_ID_KEY, userId);
        intent.putExtra(CourseLessonsActivity.COURSE_ID_KEY, courseId);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .setContentTitle("إضافة درس جديد")
                .setContentText(notificationText)
                .setSubText(userName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        0);
            } else {
                notificationManager.notify(new Random().nextInt(), notification);
            }
        } else {
            notificationManager.notify(new Random().nextInt(), notification);
        }
    }
}
