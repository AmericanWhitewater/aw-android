package com.takescoop.americanwhitewaterandroid.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.AWApplication;

import java.util.List;

public enum SharedPreferencesManager {
    Instance;

    private static final String ARRAY_DELIMITER = ",,,";
    private static final String ACCOUNT_PREFERENCES = "account_preferences";

    private SharedPreferences getSharedPrefs() {
        return AWApplication.getContext().getSharedPreferences(ACCOUNT_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveListToPrefs(String key, List<String> list) {
        String listString = TextUtils.join(ARRAY_DELIMITER, list);
        getSharedPrefs().edit().putString(key, listString).commit();
    }

    public List<String> getListFromPrefs(String key) {
        String listString = getSharedPrefs().getString(key, "");
        return Lists.newArrayList(TextUtils.split(listString, ARRAY_DELIMITER));
    }
}
