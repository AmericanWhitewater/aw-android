package com.takescoop.americanwhitewaterandroid.model.api;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takescoop.americanwhitewaterandroid.model.FlowLevel;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

public class ReachSearchResponse {
    @Expose @SerializedName("id")
    private Integer id;
    @Expose @SerializedName("state")
    private String state = "";
    @Expose @SerializedName("river")
    private String river = "";
    @Expose @SerializedName("section")
    private String section = "";
    @Expose @SerializedName("altname")
    private String altname = "";
    @Expose @SerializedName("county")
    private String county;
    @Expose @SerializedName("zipcode")
    private String zipcode;
    @Expose @SerializedName("plat")
    private String plat;
    @Expose @SerializedName("plon")
    private String plon;
    @Expose @SerializedName("tlat")
    private String tlat;
    @Expose @SerializedName("tlon")
    private String tlon;
    @Expose @SerializedName("class") // Difficulty
    private String _class;
    @Expose @SerializedName("updated") // Seconds since last update
    private String updated;
    @Expose @SerializedName("reading")
    private String reading;
    @Expose @SerializedName("gauge_min")
    private String gaugeMin;
    @Expose @SerializedName("gauge_max")
    private String gaugeMax;
    @Expose @SerializedName("gauge_metric")
    private Integer gaugeMetric;
    @Expose @SerializedName("gauge_id")
    private Integer gaugeId;
    @Expose @SerializedName("gauge_reading")
    private String gaugeReading;
    @Expose @SerializedName("last_gauge_reading")
    private String lastGaugeReading;
    @Expose @SerializedName("last_gauge_updated") // In seconds since last reading
    private String lastGaugeUpdated;
    @Expose @SerializedName("gauge_estimated")
    private Boolean gaugeEstimated;
    @Expose @SerializedName("gauge_perfect")
    private Boolean gaugePerfect;
    @Expose @SerializedName("range_comment")
    private String rangeComment;
    @Expose @SerializedName("reading_formatted")
    private String readingFormatted;
    @Expose @SerializedName("reading_delta")
    private String readingDelta;
    @Expose @SerializedName("rc")
    private String rc;
    @Expose @SerializedName("color")
    private String color;
    @Expose @SerializedName("cond") // Low medium high
    private String cond;
    @Expose @SerializedName("unit")
    private String unit;

//    @Expose @SerializedName("name")
//    private String name = "";
//    @Expose @SerializedName("huc") //USGS hydrologic unit code
//    private String huc;
//    @Expose @SerializedName("skid") // Last person who edited
//    private Integer skid;
//    @Expose @SerializedName("abstract") // Abstract of description
//    private String _abstract;
//    @Expose @SerializedName("gauge_comment")
//    private String gaugeComment;
//    @Expose @SerializedName("last_journal_update") // Last gauge comment
//    private String lastJournalUpdate;
//    @Expose @SerializedName("tloc") // GIS Well known text (GIS standard)
//    private String tloc;
//    @Expose @SerializedName("ploc")
//    private String ploc;
//    @Expose @SerializedName("status") // Editor approved or not
//    private String status;
//    @Expose @SerializedName("adjusted_reach_class") // Adjusted by flow
//    private Object adjustedReachClass;
//    @Expose @SerializedName("gauge_important") // How gages compete to be the primary
//    private Boolean gaugeImportant;
//    @Expose @SerializedName("units") // User reported
//    private String units;

    public ReachSearchResult toModel() {
        ReachSearchResult.Builder builder = new ReachSearchResult.Builder();

        String name;
        if (!TextUtils.isEmpty(altname)) {
            name = altname;
        } else {
            name = section;
        }

        LatLng putInLatLng = ApiUtils.parseLatLng(plat, plon);

        builder.setId(id)
                .setName(name)
                .setRiver(river)
                .setDifficulty(_class)
                .setLastGaugeReading(readingFormatted)
                .setFlowLevel(FlowLevel.fromAWApi(cond))
                .setPutInLatLng(putInLatLng);

        return builder.build();
    }
}
