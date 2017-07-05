package com.takescoop.americanwhitewaterandroid.model;

import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.utility.SharedPreferencesManager;

import java.util.List;

public enum FavoriteManager {
    Instance;
    private static final String FAVORITES_PREFS_KEY = "favorites_list";

    List<Integer> favoriteReachIds = Lists.newArrayList();

    public void addFavorite(Integer reachId) {
        favoriteReachIds.add(reachId);

        persistFavorites(favoriteReachIds); // Not the most performant...
    }

    public void removeFavorite(Integer reachId) {
        favoriteReachIds.remove(reachId);

        persistFavorites(favoriteReachIds);
    }

    public boolean hasFavorite() {
        return favoriteReachIds.size() > 0;
    }

    public boolean isFavorite(Integer reachId) {
        return favoriteReachIds.contains(reachId);
    }

    public boolean toggleFavorite(Integer reachId) {
        if (isFavorite(reachId)) {
            removeFavorite(reachId);
            return false;
        } else {
            addFavorite(reachId);
            return true;
        }
    }

    public List<Integer> getFavoriteReachIds() {
        return favoriteReachIds;
    }

    public void persistFavorites(List<Integer> favoriteReachIds) {
        List<String> stringList = Lists.newArrayList();
        for (Integer reachId : favoriteReachIds) {
            stringList.add(reachId.toString());
        }
        SharedPreferencesManager.Instance.saveListToPrefs(FAVORITES_PREFS_KEY, stringList);
    }

    public List<Integer> retrieveFavorites() {
        List<String> stringList = SharedPreferencesManager.Instance.getListFromPrefs(FAVORITES_PREFS_KEY);

        List<Integer> reachIds = Lists.newArrayList();
        for (String string : stringList) {
            reachIds.add(Integer.parseInt(string));
        }

        favoriteReachIds = reachIds;

        return reachIds;
    }
}
