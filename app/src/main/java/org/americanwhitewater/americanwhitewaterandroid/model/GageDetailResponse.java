package org.americanwhitewater.americanwhitewaterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.americanwhitewater.americanwhitewaterandroid.model.api.GageResponse;
import org.americanwhitewater.americanwhitewaterandroid.model.api.ReachSearchResponse;

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
