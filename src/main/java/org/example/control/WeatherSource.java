package org.example.control;

import com.google.gson.*;
import okhttp3.*;
import org.example.model.Location;
import org.example.model.Weather;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherSource {

    public WeatherSource() {
    }

    public List<Weather> getWeather(Location location, String apiKey) {
        List<Weather> weathers = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon=" +
                location.getLon() + "&appid=" + apiKey;
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            String responseString = responseBody.string();

            JsonArray filteredEntries = new JsonArray();
            JsonObject jsonObject = new Gson().fromJson(responseString, JsonObject.class);
            JsonArray responseList = jsonObject.getAsJsonArray("list");

            for (JsonElement element : responseList) {
                JsonObject entry = element.getAsJsonObject();
                String dt_txt = entry.get("dt_txt").getAsString();

                if (dt_txt.endsWith("12:00:00")) {
                    filteredEntries.add(entry);
                }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

        } catch (IOException e) {
            System.out.println("Connection failed");
            throw new RuntimeException(e);
        }
        return weathers;
    }
}
