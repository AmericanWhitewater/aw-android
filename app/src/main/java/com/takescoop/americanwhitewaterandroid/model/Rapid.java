package com.takescoop.americanwhitewaterandroid.model;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rapid {
    @SerializedName("reachid")
    @Expose
    private Integer reachid;
    @SerializedName("rapidid")
    @Expose
    private Integer rapidid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("difficulty")
    @Expose
    private Object difficulty;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("photoid")
    @Expose
    private Integer photoid;
    @SerializedName("videoid")
    @Expose
    private Object videoid;
    @SerializedName("isputin")
    @Expose
    private Integer isputin;
    @SerializedName("istakeout")
    @Expose
    private Integer istakeout;
    @SerializedName("isaccess")
    @Expose
    private Integer isaccess;
    @SerializedName("isportage")
    @Expose
    private Integer isportage;
    @SerializedName("ishazard")
    @Expose
    private Integer ishazard;
    @SerializedName("iswaterfall")
    @Expose
    private Integer iswaterfall;
    @SerializedName("isplayspot")
    @Expose
    private Integer isplayspot;
    @SerializedName("rlat")
    @Expose
    private String rlat;
    @SerializedName("rlon")
    @Expose
    private String rlon;
    @SerializedName("approximate")
    @Expose
    private Boolean approximate;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("rloc")
    @Expose
    private Object rloc;

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        if (TextUtils.isEmpty(rlat) || TextUtils.isEmpty(rlon)) {
            return null;
        }

        try {
            double lat = Double.parseDouble(rlat);
            double lng = Double.parseDouble(rlon);
            if (lat == 0 && lng == 0) { // Weird case from the api
                return null;
            }

            return new LatLng(lat, lng);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
