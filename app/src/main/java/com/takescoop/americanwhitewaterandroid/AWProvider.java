package com.takescoop.americanwhitewaterandroid;

import com.takescoop.americanwhitewaterandroid.model.FavoriteManager;
import com.takescoop.americanwhitewaterandroid.model.FilterManager;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;
import com.takescoop.americanwhitewaterandroid.utility.SharedPreferencesManager;

// This class exists so that we can move to dependency injection in the future.
public enum AWProvider {
    Instance;

    public AWApi awApi() {
        return AWApi.Instance;
    }

    public FilterManager getFilterManager() {
        return FilterManager.Instance;
    }

    public FavoriteManager getFavoriteManager() {
        return FavoriteManager.Instance;
    }

    public SharedPreferencesManager getSharedPreferencesManager() {
        return SharedPreferencesManager.Instance;
    }
}
