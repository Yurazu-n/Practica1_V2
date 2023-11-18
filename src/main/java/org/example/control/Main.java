package org.example.control;

import org.example.view.WeatherInterface;

import java.sql.SQLException;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            WeatherControl weatherControl = new WeatherControl();
            try {
                weatherControl.execute(args[0], args[1]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("The tables are going to be updated every 6 hours");
        };
        scheduler.scheduleAtFixedRate(task, 0, 6, TimeUnit.HOURS);

        WeatherInterface weatherInterface = new WeatherInterface();
        weatherInterface.run(args[0]);

    }
}