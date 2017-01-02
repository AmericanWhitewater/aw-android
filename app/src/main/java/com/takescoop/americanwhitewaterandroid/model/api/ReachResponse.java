package com.takescoop.americanwhitewaterandroid.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takescoop.americanwhitewaterandroid.model.Rapid;

import java.util.List;

public class ReachResponse {
    @Expose @SerializedName("CContainerViewJSON_view")
    public ReachContainerResponse reachResponse;

    @Expose @SerializedName("CRiverRapidsGadgetJSON_view-rapids")
    public RapidsResponse rapidsResponse;

    private static class ReachContainerResponse {
        @Expose @SerializedName("info")
        public ReachDetailResponse info;
        @Expose @SerializedName("gages")
        public List<GageResponse> gages;
    }

    private static class RapidsResponse {
        @Expose @SerializedName("rapids")
        public List<Rapid> rapids;
    }
}

