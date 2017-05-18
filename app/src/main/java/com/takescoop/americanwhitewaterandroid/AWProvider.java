package com.takescoop.americanwhitewaterandroid;

import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

// This class exists so that we can move to dependency injection in the future.
public enum AWProvider {
    Instance;

    public AWApi awApi() {
        return AWApi.Instance;
    }
}
