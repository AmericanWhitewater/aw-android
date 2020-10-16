package org.americanwhitewater.americanwhitewaterandroid.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.Lists;

import org.threeten.bp.Instant;

import java.util.List;

public class Gage {
    private Integer id;
    private String name = "";
    private String currentLevel = "";
    private Instant lastUpdated;
    private String unit = "";
    private Double delta;
    private int deltaTimeInterval;
    private String gageComment = "";
    private String min = "";
    private String max = "";
    private String source = "";
    private String sourceId = "";
    private Integer awGageMetricId;
    private FlowLevel flowLevel;
    private List<ReachSearchResult> linkedReaches = Lists.newArrayList();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public String getUnit() {
        return unit;
    }

    public Double getDelta() {
        return delta;
    }

    public int getDeltaTimeInterval() {
        return deltaTimeInterval;
    }

    public String getGageComment() {
        return gageComment;
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

    //TODO
    public LatLng getLocation() {
        return null;
    }

    public Integer getAwGageMetricId() {
        return awGageMetricId;
    }

    public FlowLevel getFlowLevel() {
        return flowLevel;
    }

    public List<ReachSearchResult> getLinkedReaches() {
        return linkedReaches;
    }

    public void setLinkedReaches(List<ReachSearchResult> linkedReaches) {
        this.linkedReaches = linkedReaches;
    }

    public static class Builder {
        private Integer id;
        private String name = "";
        private String currentLevel = "";
        private Instant lastUpdated;
        private String unit = "";
        private Double delta;
        private int deltaTimeInterval;
        private String gageComment = "";
        private String min = "";
        private String max = "";
        private String source = "";
        private String sourceId = "";
        private Integer awGageMetricId;
        private FlowLevel flowLevel;

        public Gage build() {
            Gage gage = new Gage();

            gage.id = this.id;
            gage.name = this.name;
            gage.currentLevel = this.currentLevel;
            gage.lastUpdated = this.lastUpdated;
            gage.unit = this.unit;
            gage.delta = this.delta;
            gage.deltaTimeInterval = this.deltaTimeInterval;
            gage.gageComment = this.gageComment;
            gage.min = this.min;
            gage.max = this.max;
            gage.source = this.source;
            gage.sourceId = this.sourceId;
            gage.awGageMetricId = awGageMetricId;
            gage.flowLevel = flowLevel;

            return gage;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCurrentLevel(String currentLevel) {
            this.currentLevel = currentLevel;
            return this;
        }

        public Builder setLastUpdated(Instant lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder setDelta(Double delta) {
            this.delta = delta;
            return this;
        }

        public Builder setDeltaTimeInterval(int deltaTimeInterval) {
            this.deltaTimeInterval = deltaTimeInterval;
            return this;
        }

        public Builder setGageComment(String gageComment) {
            this.gageComment = gageComment;
            return this;
        }

        public Builder setMin(String min) {
            this.min = min;
            return this;
        }

        public Builder setMax(String max) {
            this.max = max;
            return this;
        }

        public Builder setSource(String source) {
            this.source = source;
            return this;
        }

        public Builder setSourceId(String sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder setGageMetricId(Integer metricId) {
            this.awGageMetricId = metricId;
            return this;
        }

        public Builder setFlowLevel(FlowLevel flowLevel) {
            this.flowLevel = flowLevel;
            return this;
        }
    }
}
