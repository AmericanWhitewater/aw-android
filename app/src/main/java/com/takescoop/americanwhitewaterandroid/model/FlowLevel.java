package com.takescoop.americanwhitewaterandroid.model;

public enum FlowLevel {
    Low("low"), Runnable("run"), High("hig"), Frozen(null), NoInfo(null);

    private String apiQueryCode;

    FlowLevel(String apiQueryCode) {
        this.apiQueryCode = apiQueryCode;
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
}
