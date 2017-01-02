package com.takescoop.americanwhitewaterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.Instant;

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
    }
}
