package org.example.control;

import org.example.view.WeatherInterface;

import java.sql.SQLException;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws MyExecutionException {
        WeatherControl weatherControl = new WeatherControl();
        weatherControl.execute(args[0], args[1]);
    }
}