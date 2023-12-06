package org.example.model;
import org.example.control.MyExecutionException;
import java.util.List;

public interface WeatherStorage {
    void save(String path, Location location, List<Weather> weathers) throws MyExecutionException;
}
