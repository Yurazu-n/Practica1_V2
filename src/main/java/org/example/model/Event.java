package org.example.model;

public class Event {
    private String ts;
    private String ss;
    private String predictionTime;
    private Location location;

    public Event(String ts, String ss, String predictionTime, Location location) {
        this.ts = ts;
        this.ss = ss;
        this.predictionTime = predictionTime;
        this.location = location;
    }

    public String getTs() {
        return ts;
    }

    public String getSs() {
        return ss;
    }

    public String getPredictionTime() {
        return predictionTime;
    }

    public Location getLocation() {
        return location;
    }
}
