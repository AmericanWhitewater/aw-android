package com.takescoop.americanwhitewaterandroid.model.api;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReachDetailResponse {
    @Expose @SerializedName("id")
    private Integer id;
    @Expose @SerializedName("river")
    private String river = "";
    @Expose @SerializedName("section")
    private String section = "";
    @Expose @SerializedName("altname")
    private String altname = "";
    @Expose @SerializedName("county")
    private String county = "";
    @Expose @SerializedName("zipcode")
    private String zipcode = "";
    @Expose @SerializedName("length")
    private String length;
    @Expose @SerializedName("avggradient")
    private Integer avggradient;
    @Expose @SerializedName("maxgradient")
    private Integer maxgradient;
    @Expose @SerializedName("gaugeinfo")
    private String gaugeinfo = "";
    @Expose @SerializedName("description")
    private String description = "";
    @Expose @SerializedName("photoid")
    private Integer photoid;
    @Expose @SerializedName("plat")
    private String plat;
    @Expose @SerializedName("plon")
    private String plon;
    @Expose @SerializedName("tlat")
    private String tlat;
    @Expose @SerializedName("tlon")
    private String tlon;
    @Expose @SerializedName("class")
    private String _class = "";
    @Expose @SerializedName("ploc")
    private String ploc;
    @Expose @SerializedName("tloc")
    private String tloc;
    @Expose @SerializedName("shuttledetails")
    private String shuttledetails = ""; // Text directions
    @Expose @SerializedName("abstract")
    private Object _abstract = "";
    @Expose @SerializedName("custom_destination")
    private Object customDestination; // Approximates actual location if Google isn't going to geocode properly
    @Expose @SerializedName("states")
    private List<String> states = Lists.newArrayList();
    @Expose @SerializedName("gaugeid")
    private Integer gaugeid;

//    @Expose @SerializedName("agency")
//    public Object agency;
//    @Expose @SerializedName("permitid")
//    public Object permitid;
//    @Expose @SerializedName("huc")
//    public String huc;
//    @Expose @SerializedName("prrn")
//    public Object prrn;
//    @Expose @SerializedName("trrn")
//    public Object trrn;
//    @Expose @SerializedName("skid")
//    public Object skid;
//    @Expose @SerializedName("status")
//    public String status;
//    @Expose @SerializedName("edited")
//    public Instant edited;
//    @Expose @SerializedName("is_final")
//    public Boolean isFinal;
//    @Expose @SerializedName("revision")
//    public Integer revision;
//    @Expose @SerializedName("was_final")
//    public Boolean wasFinal;
//    @Expose @SerializedName("thumboverride")
//    public Boolean thumboverride;
//    @Expose @SerializedName("direction_default")
//    public Integer directionDefault; // Put in or takeout for custom destination
//    @Expose @SerializedName("revision_comment")
//    public Object revisionComment;
//    @Expose @SerializedName("permiturl")
//    public Object permiturl;
//    @Expose @SerializedName("permitinfo")
//    public Object permitinfo;


    public Integer getId() {
        return id;
    }

    public String getRiver() {
        return river;
    }

    public String getSection() {
        return section;
    }

    public String getAltname() {
        return altname;
    }

    public String getCounty() {
        return county;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getLength() {
        return length;
    }

    public Integer getAvggradient() {
        return avggradient;
    }

    public Integer getMaxgradient() {
        return maxgradient;
    }

    public String getGaugeinfo() {
        return gaugeinfo;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPhotoid() {
        return photoid;
    }

    public LatLng getPutinLatLng() {
        return ApiUtils.parseLatLng(plat, plon);
    }

    public LatLng getTakeoutLatLng() {
        return ApiUtils.parseLatLng(tlat, tlon);
    }

    public String get_class() {
        return _class;
    }

    public String getShuttledetails() {
        return shuttledetails;
    }

    public Object get_abstract() {
        return _abstract;
    }

    public Object getCustomDestination() {
        return customDestination;
    }

    public List<String> getStates() {
        return states;
    }

    public Integer getGaugeid() {
        return gaugeid;
    }
}
