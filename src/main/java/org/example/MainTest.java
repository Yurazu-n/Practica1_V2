package org.example;

import org.example.control.WeatherControl;
import org.example.view.WeatherInterface;

import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) throws SQLException, InterruptedException {
        WeatherInterface weatherInterface = new WeatherInterface();
        WeatherControl weatherControl = new WeatherControl();
        weatherControl.execute(args[0], args[1]);

        weatherInterface.run(args[0]);
    }
}