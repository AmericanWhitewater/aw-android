package com.takescoop.americanwhitewaterandroid.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takescoop.americanwhitewaterandroid.model.Gage;

import org.threeten.bp.Instant;
import org.threeten.bp.temporal.ChronoUnit;

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
    private String gaugeComment;
//    @Expose @SerializedName("gauge_data")
//    private Object gaugeData;
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

    public Gage toModel() {
        Gage.Builder builder = new Gage.Builder();

        Double delta = null;
        try {
            double currentReading = Double.parseDouble(gaugeReading);
            double lastReading = Double.parseDouble(lastGaugeReading);

            delta = currentReading - lastReading;
        } catch (NumberFormatException e) {

        }

        Integer lastUpdate = null;
        try {
            lastUpdate = Integer.parseInt(lastGaugeUpdated);
        } catch (NumberFormatException e) {

        }

        builder.setId(gaugeId)
                .setName(gaugeName)
                .setCurrentLevel(gaugeReading)
                .setLastUpdated(Instant.now().minus(lastUpdate, ChronoUnit.SECONDS))
                .setUnit(GageUnit.findById(metricid).getUnit())
                .setDelta(delta)
                .setDeltaTimeInterval(lastUpdate)
                .setGageComment(gaugeComment)
                .setMin(min)
                .setMax(max)
                .setSource(source)
                .setSourceId(sourceId);

        return builder.build();
    }
}
