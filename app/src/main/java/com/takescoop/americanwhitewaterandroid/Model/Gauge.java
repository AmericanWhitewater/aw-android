package com.takescoop.americanwhitewaterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gauge {
    @SerializedName("dhid")
    @Expose
    public Integer dhid;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("did")
    @Expose
    public Integer did;
    @SerializedName("gauge_name")
    @Expose
    public String gaugeName;
    @SerializedName("updated")
    @Expose
    public String updated;
    @SerializedName("epoch")
    @Expose
    public String epoch;
    @SerializedName("reading")
    @Expose
    public String reading;
    @SerializedName("gauge_metric")
    @Expose
    public Integer gaugeMetric;
    @SerializedName("gauge_min")
    @Expose
    public String gaugeMin;
    @SerializedName("gauge_max")
    @Expose
    public String gaugeMax;
//    @SerializedName("obs_id")
//    @Expose
//    public Integer obsId;
    @SerializedName("gauge_id")
    @Expose
    public Integer gaugeId;
    @SerializedName("gauge_reading")
    @Expose
    public String gaugeReading;
    @SerializedName("last_gauge_reading")
    @Expose
    public String lastGaugeReading;
    @SerializedName("last_gauge_updated")
    @Expose
    public String lastGaugeUpdated;
    @SerializedName("gauge_comment")
    @Expose
    public Object gaugeComment;
    @SerializedName("gauge_data")
    @Expose
    public Object gaugeData;
    @SerializedName("range_comment")
    @Expose
    public String rangeComment;
    @SerializedName("gauge_perfect")
    @Expose
    public Boolean gaugePerfect;
    @SerializedName("gauge_estimated")
    @Expose
    public Boolean gaugeEstimated;
    @SerializedName("gauge_important")
    @Expose
    public Boolean gaugeImportant;
    @SerializedName("range_min")
    @Expose
    public String rangeMin;
    @SerializedName("range_max")
    @Expose
    public String rangeMax;
    @SerializedName("adjusted_reach_class")
    @Expose
    public Object adjustedReachClass;
    @SerializedName("targetid")
    @Expose
    public Integer targetid;
    @SerializedName("sourceid")
    @Expose
    public Integer sourceid;
    @SerializedName("metricid")
    @Expose
    public Integer metricid;
    @SerializedName("min")
    @Expose
    public String min;
    @SerializedName("max")
    @Expose
    public String max;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("source_id")
    @Expose
    public String sourceId;
    @SerializedName("time_adjustment")
    @Expose
    public Object timeAdjustment;

    public Integer getDhid() {
        return dhid;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDid() {
        return did;
    }

    public String getGaugeName() {
        return gaugeName;
    }

    public String getUpdated() {
        return updated;
    }

    public String getEpoch() {
        return epoch;
    }

    public String getReading() {
        return reading;
    }

    public Integer getGaugeMetric() {
        return gaugeMetric;
    }

    public String getGaugeMin() {
        return gaugeMin;
    }

    public String getGaugeMax() {
        return gaugeMax;
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

    public Boolean getGaugePerfect() {
        return gaugePerfect;
    }

    public Boolean getGaugeEstimated() {
        return gaugeEstimated;
    }

    public Boolean getGaugeImportant() {
        return gaugeImportant;
    }

    public String getRangeMin() {
        return rangeMin;
    }

    public String getRangeMax() {
        return rangeMax;
    }

    public Object getAdjustedReachClass() {
        return adjustedReachClass;
    }

    public Integer getTargetid() {
        return targetid;
    }

    public Integer getSourceid() {
        return sourceid;
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

    public Object getTimeAdjustment() {
        return timeAdjustment;
    }
}
