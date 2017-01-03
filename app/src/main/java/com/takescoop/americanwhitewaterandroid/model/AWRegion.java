package com.takescoop.americanwhitewaterandroid.model;

public enum AWRegion {
    Alabama("stAL", "Alabama", "US"),
    Alaska("stAK", "Alaska", "US"),
    Arizona("stAZ", "Arizona", "US"),
    Arkansas("stAR", "Arkansas", "US"),
    California("stCA", "California", "US"),
    Colorado("stCO", "Colorado", "US"),
    Connecticut("stCT", "Connecticut", "US"),
    Delaware("stDE", "Delaware", "US"),
    DistrictOfColumbia("stDC", "District of Columbia", "US"),
    Florida("stFL", "Florida", "US"),
    Georgia("stGA", "Georgia", "US"),
    Hawaii("stHI", "Hawaii", "US"),
    Idaho("stID", "Idaho", "US"),
    Illinois("stIL", "Illinois", "US"),
    Indiana("stIN", "Indiana", "US"),
    Iowa("stIA", "Iowa", "US"),
    Kansas("stKS", "Kansas", "US"),
    Kentucky("stKY", "Kentucky", "US"),
    Louisiana("stLA", "Louisiana", "US"),
    Maine("stME", "Maine", "US"),
    Maryland("stMD", "Maryland", "US"),
    Massachusetts("stMA", "Massachusetts", "US"),
    Michigan("stMI", "Michigan", "US"),
    Minnesota("stMN", "Minnesota", "US"),
    Mississippi("stMS", "Mississippi", "US"),
    Missouri("stMO", "Missouri", "US"),
    Montana("stMT", "Montana", "US"),
    Nebraska("stNE", "Nebraska", "US"),
    Nevada("stNV", "Nevada", "US"),
    NewHampshire("stNH", "New Hampshire", "US"),
    NewJersey("stNJ", "New Jersey", "US"),
    NewMexico("stNM", "New Mexico", "US"),
    NewYork("stNY", "New York", "US"),
    NorthCarolina("stNC", "North Carolina", "US"),
    NorthDakota("stND", "North Dakota", "US"),
    Ohio("stOH", "Ohio", "US"),
    Oklahoma("stOK", "Oklahoma", "US"),
    Oregon("stOR", "Oregon", "US"),
    Pennsylvania("stPA", "Pennsylvania", "US"),
    PuertoRico("stPR", "Puerto Rico", "US"),
    RhodeIsland("stRI", "Rhode Island", "US"),
    SouthCarolina("stSC", "South Carolina", "US"),
    SouthDakota("stSD", "South Dakota", "US"),
    Tennessee("stTN", "Tennessee", "US"),
    Texas("stTX", "Texas", "US"),
    Utah("stUT", "Utah", "US"),
    Vermont("stVT", "Vermont", "US"),
    Virginia("stVA", "Virginia", "US"),
    Washington("stWA", "Washington", "US"),
    WestVirginia("stWV", "West Virginia", "US"),
    Wisconsin("stWI", "Wisconsin", "US"),
    Wyoming("stWY", "Wyoming", "US"),
    LowerPacific("rgLP", "Lower Pacific", "US"),
    MidAtlantic("rgMC", "MidAtlantic", "US"),
    MidWest("rgMW", "MidWest", "US"),
    NorthEast("rgNT", "North East", "US"),
    NorthWest("rgNW", "North West", "US"),
    SouthEast("rgSE", "South East", "US"),
    West("rgWT", "West", "US"),
    Alberta("stAB", "Alberta", "CA"),
    BritishColumbia("stBC", "British Columbia", "CA"),
    Manitoba("stMB", "Manitoba", "CA"),
    NewBrunswick("stNB", "New Brunswick", "CA"),
    Newfoundland("stNF", "Newfoundland", "CA"),
    NovaScotia("stNS", "Nova Scotia", "CA"),
    Nunavut("stNU", "Nunavut", "CA"),
    Ontario("stON", "Ontario", "CA"),
    PrinceEdwardIsland("stPI", "Prince Edward Island", "CA"),
    Quebec("stQC", "Quebec", "CA"),
    Saskatchewan("stSK", "Saskatchewan", "CA"),
    YukonTerritory("stYT", "Yukon Territory", "CA"),
    CostaRica("stCR", "Costa Rica", "CS"),
    DominicanRepublic("stLV", "Dominican Republic", "DR"),
    Mexico("stMX", "Mexico", "MX"),
    International("rgIN", "International", "N/A");

    private String code;
    private String title;
    private String country;

    AWRegion(String code, String title, String country) {
        this.code = code;
        this.title = title;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }
}