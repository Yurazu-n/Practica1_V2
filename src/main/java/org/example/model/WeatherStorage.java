package org.example.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface WeatherStorage {
    Connection connect(String path);

    void createTable(Statement statement, String islandName) throws SQLException;

    void insert(Statement statement, Weather weather) throws SQLException;

    void update(Statement statement, Weather weather);
}
