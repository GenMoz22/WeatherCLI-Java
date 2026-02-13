package com.weather;

import com.weather.api.WeatherApiClient;
import com.weather.model.CityCoordinates;
import com.weather.model.WeatherData;
import com.weather.ui.Display;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
@Slf4j
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherApiClient client = new WeatherApiClient();

        System.out.print("Inserisci il nome della città: ");
        String city = scanner.nextLine();

        if (city.trim().isEmpty()) {
            log.warn("Errore: Il nome della città non può essere vuoto!");
            scanner.close();
            return;
        }

        try {
            // 1. Ottieni Coordinate
            CityCoordinates coords = client.getCoordinates(city);
            
            if (coords != null) {
                // 2. Ottieni Meteo usando le coordinate trovate
                WeatherData weather = client.getWeatherData(coords.lat(), coords.lon());
                
                // 3. Mostra i risultati
                Display.printWeather(city, weather);
            } else {
                log.warn("Città non trovata");
            }
        } catch (Exception e) {
            log.error("Errore critico durante l'esecuzione: {}", e.getMessage(), e);
        } finally {
            scanner.close();
        }
    }
}