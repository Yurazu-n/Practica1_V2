package org.example.control;

import org.example.model.*;
import org.example.view.WeatherInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WeatherControl {
    private final List<Location> locations = new ArrayList<>(List.of(
            new Location(28.16667, -17.33333, "Tenerife"),
            new Location(28.09973, -15.41343, "GranCanaria"),
            new Location(28.96302, -13.54769, "Lanzarote"),
            new Location(28.50038, -13.86272, "Fuerteventura"),
            new Location(28.68351, -17.76421, "LaPalma"),
            new Location(27.80628, -17.915779, "ElHierro"),
            new Location(28.091631, -17.11331, "LaGomera")));

    private final WeatherProvider weatherProvider;
    private final WeatherInterface weatherInterface;
    private final PredictionPublisher eventPublisher;

    public WeatherControl() {
        this.weatherProvider = new WeatherPredictor();
        this.weatherInterface = new WeatherInterface();
        this.eventPublisher = new PredictionPublisher();
    }

    public void execute(String apiKey) throws MyExecutionException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable updateTask = () -> {
            try {
                for (Location location : getLocations()) {
                    List<Weather> weathers = getWeatherProvider().getWeather(location, apiKey);
                    for (Weather weather : weathers){
                        getEventPublisher().publishEvent(weather);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        scheduler.scheduleAtFixedRate(updateTask, 0, 6, TimeUnit.HOURS);
        //getWeatherInterface().run(path);
    }


    public List<Location> getLocations() {
        return locations;
    }

    public WeatherProvider getWeatherProvider() {
        return weatherProvider;
    }

    public WeatherInterface getWeatherInterface() {
        return weatherInterface;
    }

    public PredictionPublisher getEventPublisher() {
        return eventPublisher;
    }
}