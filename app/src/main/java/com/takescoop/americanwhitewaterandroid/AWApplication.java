package com.takescoop.americanwhitewaterandroid;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AWApplication extends Application {
    private static Context applicationContext;

    @Override public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        AndroidThreeTen.init(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static Context getContext() {
        return applicationContext;
    }

}
