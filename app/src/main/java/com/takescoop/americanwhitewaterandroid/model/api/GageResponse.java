package com.takescoop.americanwhitewaterandroid.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GageResponse {

    @Expose @SerializedName("gauge_id")
    private Integer gaugeId;
    @Expose @SerializedName("gauge_name")
    private String gaugeName;
    @Expose @SerializedName("gauge_reading")
    private String gaugeReading;
    @Expose @SerializedName("last_gauge_reading")
    private String lastGaugeReading;
    @Expose @SerializedName("last_gauge_updated")
    private String lastGaugeUpdated;
    @Expose @SerializedName("gauge_comment")
    private Object gaugeComment;
    @Expose @SerializedName("gauge_data")
    private Object gaugeData;
    @Expose @SerializedName("range_comment")
    private String rangeComment;
    @Expose @SerializedName("gauge_estimated")
    private Boolean gaugeEstimated;
    @Expose @SerializedName("gauge_important")
    private Boolean gaugeImportant;
    @Expose @SerializedName("metricid")
    private Integer metricid;
    @Expose @SerializedName("min")
    private String min;
    @Expose @SerializedName("max")
    private String max;
    @Expose @SerializedName("source")
    private String source;
    @Expose @SerializedName("source_id")
    private String sourceId;
//    @Expose @SerializedName("dhid")
//    public Integer dhid;
//    @Expose @SerializedName("id")
//    public Integer id;
//    @Expose @SerializedName("did")
//    public Integer did;
//    @Expose @SerializedName("updated")
//    public String updated;
//    @Expose @SerializedName("epoch")
//    public String epoch;
//    @Expose @SerializedName("reading")
//    public String reading;
//    @Expose @SerializedName("gauge_metric")
//    public Integer gaugeMetric;
//    @Expose @SerializedName("gauge_min")
//    public String gaugeMin;
//    @Expose @SerializedName("gauge_max")
//    public String gaugeMax;
//    @Expose @SerializedName("obs_id")
//    public Integer obsId;
//    @Expose @SerializedName("gauge_perfect")
//    public Boolean gaugePerfect;
//    @Expose @SerializedName("range_min")
//    public String rangeMin;
//    @Expose @SerializedName("range_max")
//    public String rangeMax;
//    @Expose @SerializedName("adjusted_reach_class")
//    public Object adjustedReachClass;
//    @Expose @SerializedName("targetid")
//    public Integer targetid;
//    @Expose @SerializedName("sourceid")
//    public Integer sourceid;
//    @Expose @SerializedName("time_adjustment")
//    public Object timeAdjustment;

    public String getGaugeName() {
        return gaugeName;
    }

    public Integer getGaugeId() {
        return gaugeId;
    }

    public String getGaugeReading() {
        return gaugeReading;
    }

    public String getLastGaugeReading() {
        return lastGaugeReading;
    }

    public String getLastGaugeUpdated() {
        return lastGaugeUpdated;
    }

    public Object getGaugeComment() {
        return gaugeComment;
    }

    public Object getGaugeData() {
        return gaugeData;
    }

    public String getRangeComment() {
        return rangeComment;
    }

    public Boolean getGaugeEstimated() {
        return gaugeEstimated;
    }

    public Boolean getGaugeImportant() {
        return gaugeImportant;
    }

    public Integer getMetricid() {
        return metricid;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public String getSource() {
        return source;
    }

    public String getSourceId() {
        return sourceId;
    }
}
