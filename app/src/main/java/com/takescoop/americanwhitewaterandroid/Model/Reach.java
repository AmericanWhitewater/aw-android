package com.takescoop.americanwhitewaterandroid.model;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.Lists;

import java.util.List;

public class Reach {
    private Integer id;
    private String name = "";
    private String sectionName = "";
    private String river = "";
    private Integer photoId;
    private String length = "";
    private String difficulty = "";
    private Integer avgGradient;
    private Integer maxGradient;
    private LatLng putinLatLng;
    private LatLng takeoutLatLng;
    private String description = "";
    private String shuttleDetails = "";
    private List<Gage> gages = Lists.newArrayList();
    private List<Rapid> rapids = Lists.newArrayList();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getRiver() {
        return river;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public String getLength() {
        return length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Integer getAvgGradient() {
        return avgGradient;
    }

    public Integer getMaxGradient() {
        return maxGradient;
    }

    public LatLng getPutinLatLng() {
        return putinLatLng;
    }

    public LatLng getTakeoutLatLng() {
        return takeoutLatLng;
    }

    public String getDescription() {
        return description;
    }

    public String getShuttleDetails() {
        return shuttleDetails;
    }

    // Returns the first gage or null
    @Nullable
    public Gage getGage() {
        if (gages.size() == 0) {
            return null;
        }

        return gages.get(0);
    }

    public List<Gage> getGages() {
        return gages;
    }

    public List<Rapid> getRapids() {
        return rapids;
    }

    public static class Builder {
        private Integer id;
        private String name = "";
        private String sectionName = "";
        private String river = "";
        private Integer photoId;
        private String length = "";
        private String difficulty = "";
        private Integer avgGradient;
        private Integer maxGradient;
        private LatLng putinLatLng;
        private LatLng takeoutLatLng;
        private String description = "";
        private String shuttleDetails = "";

        private List<AWRegion> states;
        private List<Gage> gages;
        private List<Rapid> rapids;
        
        public Reach build() {
            Reach reach = new Reach();
            reach.id = this.id;
            reach.name = this.name;
            reach.sectionName = this.sectionName;
            reach.river = this.river;
            reach.photoId = this.photoId;
            reach.length = this.length;
            reach.difficulty = this.difficulty;
            reach.avgGradient = this.avgGradient;
            reach.maxGradient = this.maxGradient;
            reach.putinLatLng = this.putinLatLng;
            reach.takeoutLatLng = this.takeoutLatLng;
            reach.description = this.description;
            reach.shuttleDetails = this.shuttleDetails;
            reach.gages = gages;
            reach.rapids = rapids;

            return reach;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSectionName(String sectionName) {
            this.sectionName = sectionName;
            return this;
        }

        public Builder setRiver(String river) {
            this.river = river;
            return this;
        }

        public Builder setPhotoId(Integer photoId) {
            this.photoId = photoId;
            return this;
        }

        public Builder setLength(String length) {
            this.length = length;
            return this;
        }

        public Builder setDifficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder setAvgGradient(Integer avgGradient) {
            this.avgGradient = avgGradient;
            return this;
        }

        public Builder setMaxGradient(Integer maxGradient) {
            this.maxGradient = maxGradient;
            return this;
        }

        public Builder setPutinLatLng(LatLng putinLatLng) {
            this.putinLatLng = putinLatLng;
            return this;
        }

        public Builder setTakeoutLatLng(LatLng takeoutLatLng) {
            this.takeoutLatLng = takeoutLatLng;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setShuttleDetails(String shuttleDetails) {
            this.shuttleDetails = shuttleDetails;
            return this;
        }

        public Builder setStates(List<AWRegion> states) {
            this.states = states;
            return this;
        }

        public Builder setGages(List<Gage> gages) {
            this.gages = gages;
            return this;
        }

        public Builder setRapids(List<Rapid> rapids) {
            this.rapids = rapids;
            return this;
        }
    }
}
