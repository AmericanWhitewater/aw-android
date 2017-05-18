package com.takescoop.americanwhitewaterandroid.model.managers;

import com.takescoop.americanwhitewaterandroid.model.Filter;

public enum FilterManager {
    Instance;

    private Filter filter = new Filter();

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
