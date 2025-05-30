package com.example.textproject.ui.lesson_details;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.textproject.databinding.ActivityLessonDetailsBinding;
import com.example.textproject.db.entity.Lesson;

public class LessonDetailsActivity extends AppCompatActivity {
    private ActivityLessonDetailsBinding binding;
    public static String KEY_LESSON = "lesson";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLessonDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Lesson lesson = (Lesson) getIntent().getSerializableExtra(KEY_LESSON);
        binding.progressBar.setVisibility(View.VISIBLE);

        if (lesson != null) {
            binding.tvLessonTitle.setText(lesson.getTitle());
            binding.tvLessonDescription.setText(lesson.getDescription());

            // إعداد WebView لعرض الفيديو
            WebView webView = binding.webView;
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);

            // استخراج معرف الفيديو من رابط اليوتيوب
            String videoUrl = lesson.getYoutubeLink();
            String videoId = extractYoutubeVideoId(videoUrl);

            if (videoId != null) {
                // كود HTML متجاوب لملء الشاشة بالكامل
                String html = "<html><body style=\"margin:0;padding:0;\">" +
                        "<iframe width=\"100%\" height=\"100%\" " +
                        "src=\"https://www.youtube.com/embed/" + videoId + "\" " +
                        "frameborder=\"0\" allowfullscreen></iframe>" +
                        "</body></html>";

                binding.progressBar.setVisibility(View.GONE);
                webView.loadData(html, "text/html", "utf-8");
            }
        }
    }

    private String extractYoutubeVideoId(String url) {
        if (url == null) return null;
        Uri uri = Uri.parse(url);
        if (url.contains("youtu.be")) {
            return uri.getLastPathSegment();
        } else if (url.contains("youtube.com")) {
            return uri.getQueryParameter("v");
        }
        return null;
    }
}
