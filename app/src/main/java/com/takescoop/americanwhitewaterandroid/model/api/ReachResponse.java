package com.takescoop.americanwhitewaterandroid.model.api;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.model.Rapid;
import com.takescoop.americanwhitewaterandroid.model.Reach;

import java.util.List;

public class ReachResponse {
    @Expose @SerializedName("CContainerViewJSON_view")
    public ReachContainerResponse reachResponse;

    @Expose @SerializedName("CRiverRapidsGadgetJSON_view-rapids")
    public RapidsResponse rapidsResponse;

    @Nullable
    public Reach toModel() {
        if (reachResponse == null || reachResponse.info == null) {
            return null;
        }

        String name;
        if (!TextUtils.isEmpty(reachResponse.info.getAltname())) {
            name = reachResponse.info.getAltname();
        } else {
            name = reachResponse.info.getSection();
        }

        List<Gage> gages = Lists.newArrayList();
        for (GageResponse response : reachResponse.gages) {
            gages.add(response.toModel());
        }

        Reach.Builder builder = new Reach.Builder();
        builder.setId(reachResponse.info.getId())
                .setName(name)
                .setSectionName(reachResponse.info.getSection())
                .setRiver(reachResponse.info.getRiver())
                .setPhotoId(reachResponse.info.getPhotoid())
                .setLength(reachResponse.info.getLength())
                .setDifficulty(reachResponse.info.get_class())
                .setAvgGradient(reachResponse.info.getAvggradient())
                .setMaxGradient(reachResponse.info.getMaxgradient())
                .setPutinLatLng(reachResponse.info.getPutinLatLng())
                .setTakeoutLatLng(reachResponse.info.getTakeoutLatLng())
                .setDescription(reachResponse.info.getDescription())
                .setShuttleDetails(reachResponse.info.getShuttledetails())
                .setGages(gages)
                .setRapids(rapidsResponse.rapids);

        return builder.build();
    }

    private static class ReachContainerResponse {
        @Expose @SerializedName("info")
        private ReachDetailResponse info;
        @Expose @SerializedName("gages")
        private List<GageResponse> gages = Lists.newArrayList();
    }

    private static class RapidsResponse {
        @Expose @SerializedName("rapids")
        private List<Rapid> rapids = Lists.newArrayList();
    }
}

