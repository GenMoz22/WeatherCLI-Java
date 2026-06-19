package com.weather.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.weather.model.*;
import com.weather.utils.JsonParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherApiClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public CityCoordinates getCoordinates(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity + "&count=1&language=it&format=json";

        log.info("Esecuzione Geocoding per: {}", city);
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(geoUrl)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        if (!json.contains("\"results\"")) {
            log.warn("Nessun risultato trovato per la città: {}", city);
            return null;
        }

        String firstResult = JsonParser.getObject(json, "\"results\"");
        double lat = JsonParser.getDouble(firstResult, "\"latitude\"");
        double lon = JsonParser.getDouble(firstResult, "\"longitude\"");

        return new CityCoordinates(lat, lon);
    }

    public WeatherResponse fetchFullWeather(double lat, double lon) throws Exception {
        String url = String.format(Locale.US,
            "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f" +
            "&current=temperature_2m,wind_speed_10m,uv_index,precipitation_probability" +
            "&daily=temperature_2m_max,temperature_2m_min,precipitation_probability_max&timezone=auto&forecast_days=14", 
            lat, lon);

        log.info("Richiesta API Meteo Avanzata: {}", url);
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        if (json.contains("\"error\":true")) {
            log.error("L'API ha restituito un errore: {}", json);
            throw new RuntimeException("Errore risposta API");
        }

        // Parsing Dati Correnti
        String currentPart = JsonParser.getObject(json, "\"current\"");
        WeatherData current = new WeatherData(
            JsonParser.getDouble(currentPart, "\"temperature_2m\""),
            JsonParser.getDouble(currentPart, "\"wind_speed_10m\""),
            JsonParser.getDouble(currentPart, "\"uv_index\""),
            (int) JsonParser.getDouble(currentPart, "\"precipitation_probability\"")
        );

        // Parsing Previsioni 14 Giorni
        List<DailyForecast> dailyList = parseDailyForecasts(json);

        return new WeatherResponse(current, dailyList, System.currentTimeMillis());
    }

    private List<DailyForecast> parseDailyForecasts(String json) {
        List<DailyForecast> forecasts = new ArrayList<>();
        String dailyPart = JsonParser.getObject(json, "\"daily\"");
        
        String[] dates = extractArray(dailyPart, "\"time\"");
        String[] maxs = extractArray(dailyPart, "\"temperature_2m_max\"");
        String[] mins = extractArray(dailyPart, "\"temperature_2m_min\"");
        String[] precips = extractArray(dailyPart, "\"precipitation_probability_max\"");

        for (int i = 0; i < Math.min(dates.length, 14); i++) {
            forecasts.add(new DailyForecast(
                dates[i].replace("\"", "").trim(), 
                Double.parseDouble(maxs[i].trim()), 
                Double.parseDouble(mins[i].trim()),
                (int) Double.parseDouble(precips[i].trim())
            ));
        }
        return forecasts;
    }

    private String[] extractArray(String json, String key) {
        int start = json.indexOf(key);
        if (start == -1) throw new IllegalArgumentException("Chiave non trovata nel JSON: " + key);
        
        start = json.indexOf("[", start) + 1;
        int end = json.indexOf("]", start);
        String content = json.substring(start, end);
        
        return content.split(",");
    }
}