package com.example.textproject.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textproject.databinding.ItemLessonBinding;
import com.example.textproject.db.dto.LessonCompletionStatus;
import com.example.textproject.listeners.ClickLessonListener;

import java.util.ArrayList;
import java.util.List;

public class LessonsWithCompletionStatusAdapter extends RecyclerView.Adapter<LessonsWithCompletionStatusAdapter.LessonsViewHolder> {
    private static List<LessonCompletionStatus> lessonsList;
    private final ClickLessonListener listener;

    public LessonsWithCompletionStatusAdapter(ClickLessonListener listener) {
        lessonsList = new ArrayList<>();
        this.listener = listener;
    }

    public void setLessonsList(List<LessonCompletionStatus> lessonsList) {
        LessonsWithCompletionStatusAdapter.lessonsList = lessonsList;
        notifyItemRangeChanged(0, lessonsList.size());
    }

    @NonNull
    @Override
    public LessonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLessonBinding binding = ItemLessonBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new LessonsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsViewHolder holder, int position) {
        LessonCompletionStatus lesson = lessonsList.get(position);
        holder.bind(lesson, listener);
    }

    @Override
    public int getItemCount() {
        return lessonsList.size();
    }

    public static class LessonsViewHolder extends RecyclerView.ViewHolder {
        private final ItemLessonBinding binding;

        public LessonsViewHolder(ItemLessonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(LessonCompletionStatus lesson, ClickLessonListener listener) {
            binding.tvLessonTitle.setText(lesson.getLesson().getTitle());

            // 🔒 افصل المستمع المؤقت قبل التعيين لتجنب إطلاق onCheckedChanged
            binding.cbLessonCompletions.setOnCheckedChangeListener(null);

            // ✅ عيّن الحالة
            binding.cbLessonCompletions.setChecked(lesson.isCompleted);

            // 🔁 أعد تفعيل المستمع بعد التعيين
            binding.cbLessonCompletions.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // ✅ تحقق إضافي: نفذ فقط عند تغيير المستخدم (اختياري)
                if (lesson.isCompleted != isChecked) {
                    listener.onLessonCheckedChangeListener(
                            lesson.getLesson().getLessonId(),
                            lesson.getLesson().getCourseId(),
                            isChecked
                    );
                }
            });

            // 📌 ضغط على العنصر
            binding.getRoot().setOnClickListener(view -> {
                listener.onClickLesson(lesson.getLesson());
            });
        }
    }
}
