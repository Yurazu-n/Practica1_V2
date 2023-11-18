package org.example.control;

import org.example.model.*;
import org.example.view.WeatherInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WeatherControl {
    private List<Location> locations = new ArrayList<>(List.of(
            new Location(28.16667, -17.33333, "Tenerife"),
            new Location(28.09973, -15.41343, "GranCanaria"),
            new Location(28.96302, -13.54769, "Lanzarote"),
            new Location(28.50038, -13.86272, "Fuerteventura"),
            new Location(28.68351, -17.76421, "LaPalma"),
            new Location(27.80628, -17.915779, "ElHierro"),
            new Location(28.091631, -17.11331, "LaGomera")));

    private WeatherStorage weatherStorage;
    private WeatherProvider weatherProvider;

    private WeatherInterface weatherInterface;

    public WeatherControl() {
        this.weatherProvider = new WeatherSource();
        this.weatherStorage = new WeatherDataBase();
        this.weatherInterface = new WeatherInterface();
    }

    public void execute(String path, String apiKey) throws SQLException {
        CountDownLatch tablesCreated = new CountDownLatch(1);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Connection connection = getWeatherStorage().connect(path);
        Statement statement = connection.createStatement();
        for (int i = 0; i < 7; i++) {
            getWeatherStorage().createTable(statement, getLocations().get(i).getIslandName());
        }

        Runnable updateTask = () -> {
            try {
                for (Location location : getLocations()) {
                    List<Weather> weathers = getWeatherProvider().getWeather(location, apiKey);
                    for (Weather weather : weathers) {
                        String query = "SELECT COUNT(*) FROM " + location.getIslandName()
                                + " WHERE instant ='" + weather.getInstant() + "';";
                        ResultSet resultSet = statement.executeQuery(query);
                        if (resultSet.getInt(1) > 0) {
                            getWeatherStorage().update(statement, weather);
                        } else {
                            getWeatherStorage().insert(statement, weather);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("The tables have been updated.");
        };

        scheduler.scheduleAtFixedRate(updateTask, 0, 6, TimeUnit.HOURS);
        getWeatherInterface().run(path);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public WeatherStorage getWeatherStorage() {
        return weatherStorage;
    }

    public WeatherProvider getWeatherProvider() {
        return weatherProvider;
    }

    public WeatherInterface getWeatherInterface() {
        return weatherInterface;
    }
}