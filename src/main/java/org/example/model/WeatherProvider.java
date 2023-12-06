package org.example.model;

import org.example.control.MyExecutionException;

import java.util.List;

public interface WeatherProvider {
    List<Weather> getWeather(Location location, String apiKey) throws MyExecutionException;
}
