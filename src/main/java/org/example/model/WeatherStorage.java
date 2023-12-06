package org.example.model;
import org.example.control.MyExecutionException;
import java.util.List;

public interface WeatherStorage {
    void save(String path, List<Location> locations, WeatherProvider weatherProvider, String apikey) throws MyExecutionException;
}
