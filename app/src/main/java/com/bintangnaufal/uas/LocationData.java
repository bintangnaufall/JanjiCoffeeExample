package com.bintangnaufal.uas;

public class LocationData {
    private String name;
    private Double latitude;
    private Double longitude;

    public LocationData(String name) {
        this.name = name;
    }

    public LocationData(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
