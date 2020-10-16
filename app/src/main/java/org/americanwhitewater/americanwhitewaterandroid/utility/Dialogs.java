package org.americanwhitewater.americanwhitewaterandroid.utility;

import android.widget.Toast;

import org.americanwhitewater.americanwhitewaterandroid.AWApplication;

public class Dialogs {

    public static void toast(String toastString) {
        Toast toast = Toast.makeText(AWApplication.getContext(), toastString, Toast.LENGTH_SHORT);
        toast.show();
    }
}
