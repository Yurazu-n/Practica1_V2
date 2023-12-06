package org.example.control;

import org.example.model.Location;
import org.example.model.Weather;
import org.example.model.WeatherProvider;
import org.example.model.WeatherStorage;

import java.sql.*;
import java.util.List;

public class WeatherDataBase implements WeatherStorage {

    public WeatherDataBase() {
    }

    @Override
    public void save(String path, List<Location> locations, WeatherProvider weatherProvider, String apikey) throws MyExecutionException {
        Statement statement = dataBaseCreator(locations, path);

        for (Location location : locations) {
            List<Weather> weathers = weatherProvider.getWeather(location, apikey);
            for (Weather weather : weathers) {
                String query = "SELECT COUNT(*) FROM " + location.getIslandName()
                        + " WHERE instant ='" + weather.getInstant() + "';";
                try {
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.getInt(1) > 0) {
                        update(statement, weather);
                    } else {
                        insert(statement, weather);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static Statement dataBaseCreator(List<Location> locations, String path) throws MyExecutionException {
        Statement statement = null;
        try {
            String url = "jdbc:sqlite:" + path + "/IslandDataBase.db";
            statement = DriverManager.getConnection(url).createStatement();
            for (int i = 0; i < 7; i++) {
                createTable(statement, locations.get(i).getIslandName());
            }
        } catch (SQLException e) {
            throw new MyExecutionException("Execution Error");
        }
        return statement;
    }

    private static void createTable(Statement statement, String islandName) throws MyExecutionException {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS " + islandName + "(" +
                    "temperature REAL,\n" +
                    "humidity INTEGER,\n" +
                    "windSpeed REAL,\n" +
                    "clouds INTEGER,\n" +
                    "precipitationProb REAL,\n" +
                    "location TEXT,\n" +
                    "instant TEXT)"
            );
        } catch (SQLException e) {
            throw new MyExecutionException("Execution Error");
        }
    }

    private static void insert(Statement statement, Weather weather) throws MyExecutionException {
        try {
            statement.execute("INSERT INTO " + weather.getLocation().getIslandName() +
                    " (temperature, humidity, windSpeed, clouds, precipitationProb, location, instant)\n" +
                    "VALUES(" + weather.getTemp() + "," +
                    weather.getHumidity() + "," +
                    weather.getWindSpeed() + "," +
                    weather.getClouds() + "," +
                    weather.getPrecipitationProb() + "," +
                    "'" + weather.getLocation().getIslandName() + "'," +
                    "'" + weather.getInstant() + "');"
            );
        } catch (SQLException e) {
            throw new MyExecutionException("Execution Error");
        }
    }

    private static void update(Statement statement, Weather weather) throws MyExecutionException {
        try {
            statement.execute("UPDATE " + weather.getLocation().getIslandName() +
                    " SET temperature = " + weather.getTemp() +
                    ", humidity = " + weather.getHumidity() +
                    ", windSpeed = " + weather.getWindSpeed() +
                    ", clouds = " + weather.getClouds() +
                    ", precipitationProb = " + weather.getPrecipitationProb() +
                    ", location = '" + weather.getLocation().getIslandName() + "'" +
                    " WHERE instant = '" + weather.getInstant() + "';");
        } catch (SQLException e) {
            throw new MyExecutionException("Execution Error");
        }
    }
}