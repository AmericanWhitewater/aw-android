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
    private String delta = "";
    private int deltaTimeInterval;
    private String gageComment = "";
    private String min = "";
    private String max = "";
    private String source = "";
    private String sourceId = "";
}
