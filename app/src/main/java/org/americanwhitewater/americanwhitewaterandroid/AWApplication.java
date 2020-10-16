package org.americanwhitewater.americanwhitewaterandroid;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class AWApplication extends Application {
    private static Context applicationContext;

    @Override public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        AndroidThreeTen.init(this);
    }

    public static Context getContext() {
        return applicationContext;
    }

}
