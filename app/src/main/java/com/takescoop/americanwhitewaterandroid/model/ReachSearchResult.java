package com.takescoop.americanwhitewaterandroid.model;

import com.google.android.gms.maps.model.LatLng;

// A view of a Reach and a corresponding gauge, to be shown on a search results page.
public class ReachSearchResult {
    private Integer id;
    private String name = "";
    private String river = "";
    private String difficulty = "";
    private String lastGaugeReading ="";
//    private Boolean isRising;
    private FlowLevel flowLevel;
    private LatLng putInLatLngh;

    private ReachSearchResult() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRiver() {
        return river;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getLastGaugeReading() {
        return lastGaugeReading;
    }

    public FlowLevel getFlowLevel() {
        return flowLevel;
    }

    public LatLng getPutInLatLng() {
        return putInLatLngh;
    }

    public static class Builder {
        private Integer id;
        private String name = "";
        private String river = "";
        private String difficulty = "";
        private String lastGaugeReading ="";
//        private Boolean isRising;
        private FlowLevel flowLevel;
        private LatLng putInLatLng;

        public ReachSearchResult build() {
            ReachSearchResult reachSearch = new ReachSearchResult();
            reachSearch.id = this.id;
            reachSearch.name = this.name;
            reachSearch.river = this.river;
            reachSearch.difficulty = this.difficulty;
            reachSearch.lastGaugeReading = this.lastGaugeReading;
            reachSearch.flowLevel = this.flowLevel;
            reachSearch.putInLatLngh = this.putInLatLng;

            return reachSearch;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRiver(String river) {
            this.river = river;
            return this;
        }

        public Builder setDifficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder setLastGaugeReading(String lastGaugeReading) {
            this.lastGaugeReading = lastGaugeReading;
            return this;
        }

//        public Builder setRising(Boolean rising) {
//            isRising = rising;
//            return this;
//        }

        public Builder setFlowLevel(FlowLevel flowLevel) {
            this.flowLevel = flowLevel;
            return this;
        }

        public Builder setPutInLatLng(LatLng putInLatLng) {
            this.putInLatLng = putInLatLng;
            return this;
        }
    }
}
