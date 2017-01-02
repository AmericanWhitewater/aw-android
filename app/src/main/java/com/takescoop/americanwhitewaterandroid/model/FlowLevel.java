package com.takescoop.americanwhitewaterandroid.model;

public enum FlowLevel {
    Low, Runnable, High, Frozen, NoInfo;

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
}
