package com.takescoop.americanwhitewaterandroid.model;

import com.google.android.gms.maps.model.LatLng;
import com.takescoop.americanwhitewaterandroid.model.api.GageResponse;

import java.util.List;

public class Reach {
    private Integer id;
    private String name = "";
    private String sectionName = "";
    private String river = "";
    private Integer photoid;
    private String length = "";
    private String difficulty = "";
    private Integer avgGradient;
    private Integer maxGradient;
    private LatLng putinLatLng;
    private LatLng takeoutLatLng;
    private String description = "";
    private String shuttleDetails = "";

    private List<GageResponse> gages;
    private List<Rapid> rapids;
}
