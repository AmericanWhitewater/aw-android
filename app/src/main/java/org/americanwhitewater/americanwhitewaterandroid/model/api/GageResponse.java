package org.americanwhitewater.americanwhitewaterandroid.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.americanwhitewater.americanwhitewaterandroid.model.FlowLevel;
import org.americanwhitewater.americanwhitewaterandroid.model.Gage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
    //        @Expose @SerializedName("dhid")
//    private Integer dhid;
//    @Expose @SerializedName("id")
//    private Integer id;
//    @Expose @SerializedName("did")
//    private Integer did;
//    @Expose @SerializedName("updated")
//    private String updated;
//    @Expose @SerializedName("epoch")
//    private String epoch;
//    @Expose @SerializedName("reading")
//    private String reading;
    @Expose @SerializedName("gauge_metric")
    private Integer gaugeMetric;
    //    @Expose @SerializedName("gauge_min")
//    private String gaugeMin;
//    @Expose @SerializedName("gauge_max")
//    private String gaugeMax;
//    @Expose @SerializedName("obs_id")
//    private Integer obsId;
//    @Expose @SerializedName("gauge_perfect")
//    private Boolean gaugePerfect;
//    @Expose @SerializedName("range_min")
//    private String rangeMin;
//    @Expose @SerializedName("range_max")
//    private String rangeMax;
//    @Expose @SerializedName("adjusted_reach_class")
//    private Object adjustedReachClass;
//    @Expose @SerializedName("targetid")
//    private Integer targetid;
//    @Expose @SerializedName("time_adjustment")
//    private Object timeAdjustment;
    @SerializedName("url")
    @Expose
    private String url;
    //    @SerializedName("class")
//    @Expose
//    private String _class;
    @SerializedName("rc")
    @Expose
    private Double rc;
//    @SerializedName("excluded")
//    @Expose
//    private Boolean excluded;
//    @SerializedName("rmin")
//    @Expose
//    private String rmin;
//    @SerializedName("rmax")
//    @Expose
//    private String rmax;

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

        FlowLevel flowLevel = FlowLevel.fromAWApiRCField(rc);

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
                .setSourceId(sourceId)
                .setGageMetricId(gaugeMetric)
                .setFlowLevel(flowLevel);

        return builder.build();
    }
}
