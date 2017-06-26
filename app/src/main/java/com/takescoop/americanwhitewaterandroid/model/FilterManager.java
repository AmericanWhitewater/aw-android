package com.takescoop.americanwhitewaterandroid.model;

public enum  FilterManager {
    Instance;

    private Filter filter;

    public Filter getFilter() {
        // Set a default
        if (filter == null) {
            filter = new Filter();
            filter.addRegion(AWRegion.NewHampshire);
        }

        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
