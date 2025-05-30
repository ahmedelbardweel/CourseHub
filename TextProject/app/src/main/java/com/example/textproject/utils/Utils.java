package com.example.textproject.utils;

import android.text.InputType;
import android.widget.EditText;

public class Utils {
    public static boolean showHidePassword(boolean isPasswordVisible, EditText et) {
        if (isPasswordVisible) {
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = false;
        } else {
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordVisible = true;

        }
        et.setSelection(et.getText().length());
        return isPasswordVisible;

    }


}
