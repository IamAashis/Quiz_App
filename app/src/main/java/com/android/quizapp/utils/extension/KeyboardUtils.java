package com.android.quizapp.utils.extension;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by Aashis on 31,March,2024
 */
public class KeyboardUtils {

    public static void hideKeyboard(@NonNull Fragment fragment) {
        View view = fragment.getView();
        if (view != null) {
            Activity activity = fragment.getActivity();
            if (activity != null) {
                hideKeyboard(activity, view);
            }
        }
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = activity.getWindow().getDecorView();
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(@NonNull Context context, @NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
