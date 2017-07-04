package com.takescoop.americanwhitewaterandroid.model;

public enum  FilterManager {
    Instance;

    private Filter filter;

    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter();
        }

        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
