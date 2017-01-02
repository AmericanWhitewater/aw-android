package com.takescoop.americanwhitewaterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReachDetail {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("river")
    @Expose
    public String river;
    @SerializedName("section")
    @Expose
    public String section;
    @SerializedName("altname")
    @Expose
    public String altname;
    @SerializedName("county")
    @Expose
    public String county;
    @SerializedName("zipcode")
    @Expose
    public String zipcode;
    @SerializedName("length")
    @Expose
    public String length;
    @SerializedName("avggradient")
    @Expose
    public Integer avggradient;
    @SerializedName("maxgradient")
    @Expose
    public Integer maxgradient;
    @SerializedName("agency")
    @Expose
    public Object agency;
    @SerializedName("gaugeinfo")
    @Expose
    public String gaugeinfo;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("photoid")
    @Expose
    public Integer photoid;
    @SerializedName("permitid")
    @Expose
    public Object permitid;
    @SerializedName("huc")
    @Expose
    public String huc;
    @SerializedName("plat")
    @Expose
    public String plat;
    @SerializedName("plon")
    @Expose
    public String plon;
    @SerializedName("prrn")
    @Expose
    public Object prrn;
    @SerializedName("tlat")
    @Expose
    public String tlat;
    @SerializedName("tlon")
    @Expose
    public String tlon;
    @SerializedName("trrn")
    @Expose
    public Object trrn;
    @SerializedName("skid")
    @Expose
    public Object skid;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("edited")
    @Expose
    public String edited;
    @SerializedName("is_final")
    @Expose
    public Boolean isFinal;
    @SerializedName("revision")
    @Expose
    public Integer revision;
    @SerializedName("class")
    @Expose
    public String _class;
    @SerializedName("ploc")
    @Expose
    public String ploc;
    @SerializedName("tloc")
    @Expose
    public String tloc;
    @SerializedName("was_final")
    @Expose
    public Boolean wasFinal;
    @SerializedName("thumboverride")
    @Expose
    public Boolean thumboverride;
    @SerializedName("shuttledetails")
    @Expose
    public Object shuttledetails; // Text directions
    @SerializedName("abstract")
    @Expose
    public Object _abstract;
//    @SerializedName("direction_default")
//    @Expose
//    public Integer directionDefault; // Put in or takeout for custom destination
    @SerializedName("custom_destination")
    @Expose
    public Object customDestination; // Approximates actual location if Google isn't going to geocode properly
    @SerializedName("revision_comment")
    @Expose
    public Object revisionComment;
    @SerializedName("permiturl")
    @Expose
    public Object permiturl;
    @SerializedName("permitinfo")
    @Expose
    public Object permitinfo;
    @SerializedName("states")
    @Expose
    public List<String> states = null;
    @SerializedName("gaugeid")
    @Expose
    public Integer gaugeid;

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

    public Object getAgency() {
        return agency;
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

    public Object getPermitid() {
        return permitid;
    }

    public String getHuc() {
        return huc;
    }

    public String getPlat() {
        return plat;
    }

    public String getPlon() {
        return plon;
    }

    public Object getPrrn() {
        return prrn;
    }

    public String getTlat() {
        return tlat;
    }

    public String getTlon() {
        return tlon;
    }

    public Object getTrrn() {
        return trrn;
    }

    public Object getSkid() {
        return skid;
    }

    public String getStatus() {
        return status;
    }

    public String getEdited() {
        return edited;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public Integer getRevision() {
        return revision;
    }

    public String get_class() {
        return _class;
    }

    public String getPloc() {
        return ploc;
    }

    public String getTloc() {
        return tloc;
    }

    public Boolean getWasFinal() {
        return wasFinal;
    }

    public Boolean getThumboverride() {
        return thumboverride;
    }

    public Object getShuttledetails() {
        return shuttledetails;
    }

    public Object get_abstract() {
        return _abstract;
    }

    public Object getCustomDestination() {
        return customDestination;
    }

    public Object getRevisionComment() {
        return revisionComment;
    }

    public Object getPermiturl() {
        return permiturl;
    }

    public Object getPermitinfo() {
        return permitinfo;
    }

    public List<String> getStates() {
        return states;
    }

    public Integer getGaugeid() {
        return gaugeid;
    }
}
