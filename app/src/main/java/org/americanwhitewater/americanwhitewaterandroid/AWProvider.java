package org.americanwhitewater.americanwhitewaterandroid;

import org.americanwhitewater.americanwhitewaterandroid.model.FavoriteManager;
import org.americanwhitewater.americanwhitewaterandroid.model.FilterManager;
import org.americanwhitewater.americanwhitewaterandroid.model.api.AWApi;
import org.americanwhitewater.americanwhitewaterandroid.utility.SharedPreferencesManager;

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
