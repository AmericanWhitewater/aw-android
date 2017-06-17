package com.takescoop.americanwhitewaterandroid;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;

import io.fabric.sdk.android.Fabric;

public class AWApplication extends Application {
    private static Context applicationContext;

    @Override public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        Fabric.with(this, new Crashlytics());
        AndroidThreeTen.init(this);
    }

    public static Context getContext() {
        return applicationContext;
    }

}
