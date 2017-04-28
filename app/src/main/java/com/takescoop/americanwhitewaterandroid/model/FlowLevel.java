package com.takescoop.americanwhitewaterandroid.model;

import com.takescoop.americanwhitewaterandroid.R;

public enum FlowLevel {
    // While the color is more of a view concern, it's inextricably tied to the flow level, so I'm putting it here.
    Low("low", R.color.status_yellow),
    Runnable("run", R.color.status_green),
    High("hig", R.color.status_red),
    Frozen(null, R.color.status_blue),
    NoInfo(null, R.color.status_grey);

    private String apiQueryCode;
    private int colorCode;

    FlowLevel(String apiQueryCode, int colorCode) {
        this.apiQueryCode = apiQueryCode;
        this.colorCode = colorCode;
    }

    // Map values from AW api
    public static FlowLevel fromAWApi(String flowLevel) {
        switch (flowLevel) {
            case "low":
                return Low;
            case "med":
                return Runnable;
            case "high":
                return High;
            default:
                return NoInfo;
        }
    }

    public String getApiQueryCode() {
        return apiQueryCode;
    }

    public int getColorCode() {
        return colorCode;
    }
}
