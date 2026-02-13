package com.weather.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.weather.model.CityCoordinates;
import com.weather.model.WeatherData;
import com.weather.utils.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherApiClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public CityCoordinates getCoordinates(String city) throws Exception {
        log.info("Ricerca delle coordinate della città: {}",city);
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8); // Encoding per gestire caratteri speciali
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity + "&count=1";

        HttpRequest geoRequest = HttpRequest.newBuilder()
                .uri(URI.create(geoUrl))
                .build();

        HttpResponse<String> geoResponse = client.send(geoRequest, HttpResponse.BodyHandlers.ofString());
        String geoJson = geoResponse.body();

        if (!geoJson.contains("\"results\"")) {
            log.info("geoJson non contiene risultati");
            return null;
        }

        String firstResult = JsonParser.getObject(geoJson, "\"results\"");
        double lat = JsonParser.getDouble(firstResult, "\"latitude\"");
        double lon = JsonParser.getDouble(firstResult, "\"longitude\"");

        return new CityCoordinates(lat, lon);
    }

    public WeatherData getWeatherData(double lat, double lon) throws Exception {
        log.info("Ricerca dati meteo per città lat: {} e lon: {}",lat,lon);
        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
                "&longitude=" + lon + "&current_weather=true";

        HttpRequest weatherRequest = HttpRequest.newBuilder()
                .uri(URI.create(weatherUrl))
                .build();

        HttpResponse<String> weatherResponse = client.send(weatherRequest, HttpResponse.BodyHandlers.ofString());
        String weatherJson = weatherResponse.body();

        if (!weatherJson.contains("\"current_weather\"")) {
            log.info("weatherJson non contiene risultati");
            return null;
        }

        String currentWeather = JsonParser.getObject(weatherJson, "\"current_weather\"");
        double temperature = JsonParser.getDouble(currentWeather, "\"temperature\"");

        return new WeatherData(temperature);
    }
}
