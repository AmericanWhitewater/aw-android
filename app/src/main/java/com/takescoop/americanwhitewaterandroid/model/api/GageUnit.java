package com.takescoop.americanwhitewaterandroid.model.api;


import android.support.annotation.Nullable;

public enum GageUnit {
    RC(1, "r.c.", "Status (r.c.)"),
    CFS(2, "cfs", "Flow (cfs)"),
    Precip12H(3, "in/12h", "Precip. in 12h (in/12h)"),
    Precip15Min(4, "in/15min", "Precip. in 15m (in/15min)"),
    Precip24H(5, "in/24h", "Precip. in 24h (in/24h)"),
    Precip6H(6, "in/6h", "Precip. in 6h (in/6h)"),
    PrecipPerYear(7, "in/yr", "Yearly Precip. (in/yr)"),
    Feet(8, "ft", "Feet Stage (ft)"),
    Fahrenheit(9, "? F", "Temperature (? F)"),
    AltCFS(10, "cfs", "Alt. Flow (cfs)"),
    AltFt(11, "ft", "Alt. Stage in Feet (ft)"),
    M(12, "m", "Meters Stage (m)"),
    CMS(13, "cms", "Metric Volumentric Flow (cm/s)"),
    Percent(14, "%", "Percent (%)"),
    Inches(15, "inches", "Inches Stage (inches)");

    private int id;
    private String unit;
    private String label;

    GageUnit(int id, String unit, String label) {
        this.id = id;
        this.unit = unit;
        this.label = label;
    }

    @Nullable
    public static GageUnit findById(int id) {
        for (GageUnit unit : GageUnit.values()) {
            if (unit.id == id) {
                return unit;
            }
        }
        return null;
    }

    public String getUnit() {
        return unit;
    }
}
