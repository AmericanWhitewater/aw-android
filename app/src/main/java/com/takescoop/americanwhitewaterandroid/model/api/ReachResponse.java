package com.takescoop.americanwhitewaterandroid.model.api;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takescoop.americanwhitewaterandroid.model.AWRegion;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.model.Rapid;
import com.takescoop.americanwhitewaterandroid.model.Reach;

import java.util.List;
import java.util.Map;

public class ReachResponse {
    @Expose @SerializedName("CContainerViewJSON_view")
    public ReachContainerResponse reachContainerResponse;

    @Nullable
    public Reach toModel() {
        if (reachContainerResponse == null || reachContainerResponse.reachMainResponse == null 
                || reachContainerResponse.reachMainResponse.info == null) {
            return null;
        }
        
        ReachDetailResponse reachDetailResponse = reachContainerResponse.reachMainResponse.info;

        String name;
        if (!TextUtils.isEmpty(reachDetailResponse.getAltname())) {
            name = reachDetailResponse.getAltname();
        } else {
            name = reachDetailResponse.getSection();
        }

        List<Gage> gages = Lists.newArrayList();
        try {
            for (GageResponse response : reachContainerResponse.reachMainResponse.gaugeSummary.gauges.values()) {
                gages.add(response.toModel());
            }
        } catch (NullPointerException ignored) {

        }

        Reach.Builder builder = new Reach.Builder();
        builder.setId(reachDetailResponse.getId())
                .setName(name)
                .setSectionName(reachDetailResponse.getSection())
                .setRiver(reachDetailResponse.getRiver())
                .setPhotoId(reachDetailResponse.getPhotoid())
                .setLength(reachDetailResponse.getLength())
                .setDifficulty(reachDetailResponse.get_class())
                .setAvgGradient(reachDetailResponse.getAvggradient())
                .setMaxGradient(reachDetailResponse.getMaxgradient())
                .setPutinLatLng(reachDetailResponse.getPutinLatLng())
                .setTakeoutLatLng(reachDetailResponse.getTakeoutLatLng())
                .setDescription(reachDetailResponse.getDescription())
                .setShuttleDetails(reachDetailResponse.getShuttledetails())
                .setGages(gages)
                .setRapids(reachContainerResponse.rapidsResponse.rapids);

        return builder.build();
    }

    private static class ReachContainerResponse {
        @Expose @SerializedName("CRiverMainGadgetJSON_main")
        public ReachMainResponse reachMainResponse;

        @Expose @SerializedName("CRiverRapidsGadgetJSON_view-rapids")
        public RapidsResponse rapidsResponse;
    }

    private static class ReachMainResponse {
        @Expose @SerializedName("info")
        private ReachDetailResponse info;
        @Expose @SerializedName("guagesummary")
        private GageSummaryResponse gaugeSummary;
    }

    private static class RapidsResponse {
        @Expose @SerializedName("rapids")
        private List<Rapid> rapids = Lists.newArrayList();
    }

    private static class GageSummaryResponse {
        @Expose @SerializedName("gauges")
        private Map<String, GageResponse> gauges;
    }
}

