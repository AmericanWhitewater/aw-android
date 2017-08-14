package com.takescoop.americanwhitewaterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takescoop.americanwhitewaterandroid.model.api.GageResponse;
import com.takescoop.americanwhitewaterandroid.model.api.ReachSearchResponse;

import java.util.List;

public class GageDetailResponse {
    @SerializedName("gauge") @Expose
    public GageResponse gauge;
    @SerializedName("riverinfo") @Expose
    public List<ReachSearchResponse> riverinfo = null;

    public GageResponse getGauge() {
        return gauge;
    }

    public List<ReachSearchResponse> getRiverinfo() {
        return riverinfo;
    }
}
