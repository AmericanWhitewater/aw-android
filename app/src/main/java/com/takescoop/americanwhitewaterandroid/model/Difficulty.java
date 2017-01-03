package com.takescoop.americanwhitewaterandroid.model;

public enum Difficulty {
    I(1.0, "I"),
    II(2.0, "II"),
    IIPlus(2.1, "II+"),
    IIIMinus(2.9, "III-"),
    III(3.0, "III"),
    IIIPlus(3.1, "III+"),
    IVMinus(3.9, "IV-"),
    IV(4.0, "IV"),
    IVPlus(4.1, "IV+"),
    VMinus(4.9, "V-"),
    V(5.0, "V"),
    VPlus(5.1, "V+");

    private double rank;
    private String title;

    Difficulty(double rank, String title) {
        this.rank = rank;
        this.title = title;
    }

    public double getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }
}
