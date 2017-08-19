package com.takescoop.americanwhitewaterandroid.model;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.Lists;

import java.util.List;

public class Filter {
    private List<AWRegion> regions = Lists.newArrayList();
    private LatLng currentLocation;
    private int radius;
    private FlowLevel flowLevel;
    private Difficulty difficultyLowerBound;
    private Difficulty difficultyUpperBound;

    public List<AWRegion> getRegions() {
        return regions;
    }

    public void setRegions(List<AWRegion> regions) {
        this.regions = regions;
    }

    public void addRegion(@Nullable AWRegion region) {
        if (region != null) {
            regions.add(region);
        }
    }

    public boolean hasRegion() {
        return !regions.isEmpty();
    }

    public void clearRegions() {
        regions.clear();
    }

    @Nullable
    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean hasRadius() {
        return radius > 0;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void clearRadius() {
        this.radius = 0;
    }

    @Nullable
    public FlowLevel getFlowLevel() {
        return flowLevel;
    }

    public void setFlowLevel(FlowLevel flowLevel) {
        this.flowLevel = flowLevel;
    }

    @Nullable
    public Difficulty getDifficultyLowerBound() {
        return difficultyLowerBound;
    }

    public void setDifficultyLowerBound(@Nullable Difficulty difficultyLowerBound) {
        this.difficultyLowerBound = difficultyLowerBound;
    }

    @Nullable
    public Difficulty getDifficultyUpperBound() {
        return difficultyUpperBound;
    }

    public void setDifficultyUpperBound(@Nullable Difficulty difficultyUpperBound) {
        this.difficultyUpperBound = difficultyUpperBound;
    }
}
