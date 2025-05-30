package com.example.textproject.admin_ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.textproject.databinding.DialogAddCategoryBinding;

public class AddCategoryDialog extends DialogFragment {
    private final AddCategoryListener dialogListener;

    public AddCategoryDialog(AddCategoryListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogAddCategoryBinding binding = DialogAddCategoryBinding.inflate(getLayoutInflater());

        binding.btnAdd.setOnClickListener(view -> {
            String name = binding.edtCourseName.getText().toString().trim();
            dialogListener.onClickAddCategory(name);
            dismiss();
        });

        binding.btnCancel.setOnClickListener(view -> dismiss());

        return binding.getRoot();
    }

    public interface AddCategoryListener {
        void onClickAddCategory(String name);
    }
}
