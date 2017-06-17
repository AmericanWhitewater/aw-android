package com.takescoop.americanwhitewaterandroid.utility;

import android.widget.Toast;

import com.takescoop.americanwhitewaterandroid.AWApplication;

public class Dialogs {

    public static void toast(String toastString) {
        Toast toast = Toast.makeText(AWApplication.getContext(), toastString, Toast.LENGTH_SHORT);
        toast.show();
    }
}
