package org.example.model;


public class Weather {
    private double temp;
    private int humidity;
    private double windSpeed;
    private int clouds;
    private double precipitationProb;
    private Location location;
    private String instant;

    public Weather(double temp, int humidity, double windSpeed, int clouds,
                   double precipitationProb,Location location, String instant) {
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.precipitationProb = precipitationProb;
        this.location = location;
        this.instant = instant;
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getClouds() {
        return clouds;
    }

    public double getPrecipitationProb() {
        return precipitationProb;
    }

    public Location getLocation() {
        return location;
    }

    public String getInstant() {
        return instant;
    }
}