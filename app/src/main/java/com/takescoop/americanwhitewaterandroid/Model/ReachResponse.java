package com.takescoop.americanwhitewaterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReachResponse {
    @SerializedName("info")
    @Expose
    public ReachDetail info;
    @SerializedName("gauges")
    @Expose
    public List<Gauge> gauges = null;
}
