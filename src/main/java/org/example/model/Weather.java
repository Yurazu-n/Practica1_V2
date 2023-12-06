package org.example.model;


public class Weather {
    private double temp;
    private int humidity;
    private double windSpeed;
    private int clouds;
    private double precipitationProb;
    private Event event;


    public Weather(double temp, int humidity, double windSpeed, int clouds,
                   double precipitationProb, Event event) {
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.precipitationProb = precipitationProb;
        this.event = event;
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

    public Event getEvent() {
        return event;
    }
}