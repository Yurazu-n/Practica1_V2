package org.example.control;

import org.example.model.Weather;
import org.example.model.WeatherStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class WeatherDataBase implements WeatherStorage {

    public WeatherDataBase() {
    }

    @Override
    public Connection connect(String path) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + path + "/IslandDataBase.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public void createTable(Statement statement, String islandName) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS " + islandName + "(" +
                "temperature REAL,\n" +
                "humidity INTEGER,\n" +
                "windSpeed REAL,\n" +
                "clouds INTEGER,\n" +
                "precipitationProb REAL,\n" +
                "location TEXT,\n" +
                "instant TEXT)"
        );
    }

    @Override
    public void insert(Statement statement, Weather weather) throws SQLException {
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
    }

    @Override
    public void update(Statement statement, Weather weather) {
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
            throw new RuntimeException(e);
        }
    }
}