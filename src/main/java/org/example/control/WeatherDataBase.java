package org.example.control;

import org.example.model.Location;
import org.example.model.Weather;
import org.example.model.WeatherStorage;

import java.sql.*;
import java.util.List;

public class WeatherDataBase implements WeatherStorage {

    public WeatherDataBase() {
    }

    @Override
    public void save(String path, Location location, List<Weather> weathers) throws MyExecutionException {
        Statement statement = dataBaseCreator(location, path);

        for (Weather weather : weathers) {
            String query = "SELECT COUNT(*) FROM " + location.getIslandName()
                    + " WHERE instant ='" + weather.getEvent().getPredictionTime() + "';";
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


    private static Statement dataBaseCreator(Location location, String path) throws MyExecutionException {
        Statement statement = null;
        try {
            String url = "jdbc:sqlite:" + path + "/IslandDataBase.db";
            statement = DriverManager.getConnection(url).createStatement();
            createTable(statement, location.getIslandName());

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
            statement.execute("INSERT INTO " + weather.getEvent().getLocation().getIslandName() +
                    " (temperature, humidity, windSpeed, clouds, precipitationProb, location, instant)\n" +
                    "VALUES(" + weather.getTemp() + "," +
                    weather.getHumidity() + "," +
                    weather.getWindSpeed() + "," +
                    weather.getClouds() + "," +
                    weather.getPrecipitationProb() + "," +
                    "'" + weather.getEvent().getLocation().getIslandName() + "'," +
                    "'" + weather.getEvent().getPredictionTime() + "');"
            );
        } catch (SQLException e) {
            throw new MyExecutionException("Execution Error");
        }
    }

    private static void update(Statement statement, Weather weather) throws MyExecutionException {
        try {
            statement.execute("UPDATE " + weather.getEvent().getLocation().getIslandName() +
                    " SET temperature = " + weather.getTemp() +
                    ", humidity = " + weather.getHumidity() +
                    ", windSpeed = " + weather.getWindSpeed() +
                    ", clouds = " + weather.getClouds() +
                    ", precipitationProb = " + weather.getPrecipitationProb() +
                    ", location = '" + weather.getEvent().getLocation().getIslandName() + "'" +
                    " WHERE instant = '" + weather.getEvent().getPredictionTime() + "';");
        } catch (SQLException e) {
            throw new MyExecutionException("Execution Error");
        }
    }
}