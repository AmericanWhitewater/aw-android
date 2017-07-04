package com.takescoop.americanwhitewaterandroid.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.utility.SharedPreferencesManager;

import java.util.List;

public enum FilterManager {
    Instance;

    private static final String REGIONS_KEY = "regions";
    private static final String BOUNDS_KEY = "bounds";
    private static final String FLOW_LEVEL_KEY = "flow_level";
    private static final String DIFFICULTY_LOWER_KEY = "difficulty_lower";
    private static final String DIFFICULTY_UPPER_KEY = "difficulty_upper";

    private SharedPreferencesManager sharedPrefsManager = SharedPreferencesManager.Instance;

    private Filter filter;

    public Filter getFilter() {
        if (filter == null) {
            filter = retrieve();
        }

        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
        save();
    }

    ///////////////////////////////////////////////////////////////////////////
    // PERSISTENCE
    ///////////////////////////////////////////////////////////////////////////

    // TODO handle persistence more elegantly
    public void save() {
        List<String> stringList = Lists.newArrayList();
        for (AWRegion region : filter.getRegions()) {
            stringList.add(region.toString());
        }

        sharedPrefsManager.saveListToPrefs(REGIONS_KEY, stringList);

        if (filter.getBounds() != null) {
            saveStringToPrefs(BOUNDS_KEY, getStringFromBounds(filter.getBounds()));
        }

        if (filter.getFlowLevel() != null) {
            saveStringToPrefs(FLOW_LEVEL_KEY, filter.getFlowLevel().toString());
        }

        if (filter.getDifficultyLowerBound() != null) {
            saveStringToPrefs(DIFFICULTY_LOWER_KEY, filter.getDifficultyLowerBound().toString());
        }
        if (filter.getDifficultyUpperBound() != null) {
            saveStringToPrefs(DIFFICULTY_UPPER_KEY, filter.getDifficultyUpperBound().toString());
        }
    }

    private Filter retrieve() {
        Filter filter = new Filter();

        List<String> stringList = SharedPreferencesManager.Instance.getListFromPrefs(REGIONS_KEY);

        if (!stringList.isEmpty()) {
            List<AWRegion> regions = Lists.newArrayList();
            for (String string : stringList) {
                regions.add(AWRegion.valueOf(string));
            }

            filter.setRegions(regions);
        }

        // Not the most elegant
        try {
            filter.setBounds(getBoundsFromSavedString(getStringFromPrefs(BOUNDS_KEY)));
        } catch (IllegalArgumentException e) {

        }

        try {
            filter.setFlowLevel(FlowLevel.valueOf(getStringFromPrefs(FLOW_LEVEL_KEY)));
        } catch (IllegalArgumentException e) {

        }

        try {
            filter.setDifficultyLowerBound(Difficulty.valueOf(getStringFromPrefs(DIFFICULTY_LOWER_KEY)));
        } catch (IllegalArgumentException e) {

        }

        try {
            filter.setDifficultyUpperBound(Difficulty.valueOf(getStringFromPrefs(DIFFICULTY_UPPER_KEY)));
        } catch (IllegalArgumentException e) {

        }

        return filter;
    }

    private void saveStringToPrefs(String key, String string) {
        sharedPrefsManager.getSharedPrefs().edit().putString(key, string).apply();
    }

    private String getStringFromPrefs(String key) {
        return sharedPrefsManager.getSharedPrefs().getString(key, "");
    }

    private String getStringFromBounds(LatLngBounds bounds) {
        return bounds.northeast.latitude + "," + bounds.northeast.longitude + "," + bounds.southwest.latitude + "," + bounds.southwest.longitude;
    }

    @Nullable
    private LatLngBounds getBoundsFromSavedString(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }

        List<String> stringList = Lists.newArrayList(string.split(","));
        if (stringList.size() != 4) {
            return null;
        }

        LatLng ne = new LatLng(Double.parseDouble(stringList.get(0)), Double.parseDouble(stringList.get(1)));
        LatLng sw = new LatLng(Double.parseDouble(stringList.get(2)), Double.parseDouble(stringList.get(3)));

        return new LatLngBounds(ne, sw);
    }
}