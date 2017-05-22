package com.takescoop.americanwhitewaterandroid;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;

import io.fabric.sdk.android.Fabric;

public class AWApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        AndroidThreeTen.init(this);
    }
}
