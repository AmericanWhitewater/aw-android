package com.takescoop.americanwhitewaterandroid.model;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.Lists;

import java.util.List;

public class Filter {
    private List<AWRegion> regions = Lists.newArrayList();
    private LatLngBounds bounds;
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
    public LatLngBounds getBounds() {
        return bounds;
    }

    public void setBounds(LatLngBounds bounds) {
        this.bounds = bounds;
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
