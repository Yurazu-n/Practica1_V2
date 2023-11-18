package org.example.model;

public class Location {

    private Double lat, lon;
    private String islandName;

    public Location(Double lat, Double lon, String islandName) {
        this.lat = lat;
        this.lon = lon;
        this.islandName = islandName;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getIslandName() {
        return islandName;
    }
}
