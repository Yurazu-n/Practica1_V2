package org.example.control;

import com.google.gson.*;
import okhttp3.*;
import org.example.model.Location;
import org.example.model.Weather;
import org.example.model.WeatherProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class WeatherSource implements WeatherProvider {

    public WeatherSource() {
    }

    @Override
    public List<Weather> getWeather(Location location, String apiKey) throws MyExecutionException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon=" +
                location.getLon() + "&appid=" + apiKey;
        Request request = new Request.Builder().url(url).build();
        try {
            ResponseBody responseBody = client.newCall(request).execute().body();
            String responseString = responseBody.string();

            JsonArray filteredEntries = predictionFilter(responseString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return weatherListContructor(filteredEntries, formatter, location);

        } catch (IOException e) {
            throw new MyExecutionException("Execution Error");
        }
    }

    private static JsonArray predictionFilter(String response) {
        JsonArray filteredEntries = new JsonArray();
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        JsonArray responseList = jsonObject.getAsJsonArray("list");
        for (JsonElement element : responseList) {
            JsonObject entry = element.getAsJsonObject();
            String dt_txt = entry.get("dt_txt").getAsString();

            if (dt_txt.endsWith("12:00:00")) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }

    private static List<Weather> weatherListContructor(JsonArray filteredEntries, DateTimeFormatter formatter,
                                                       Location location) {
        List<Weather> weathers = new ArrayList<>();
        for (JsonElement element : filteredEntries) {
            JsonObject listElement = element.getAsJsonObject();

            weathers.add(new Weather(
                    listElement.getAsJsonObject("main").get("temp").getAsDouble(),
                    listElement.getAsJsonObject("main").get("humidity").getAsInt(),
                    listElement.getAsJsonObject("wind").get("speed").getAsDouble(),
                    listElement.getAsJsonObject("clouds").get("all").getAsInt(),
                    listElement.get("pop").getAsInt(),
                    location,
                    LocalDateTime.parse(listElement.get("dt_txt").getAsString(), formatter).toLocalDate().toString()
            ));
        }
       return weathers;
    }
}


