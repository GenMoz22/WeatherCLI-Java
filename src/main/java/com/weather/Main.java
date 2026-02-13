package com.weather;

import com.weather.api.WeatherApiClient;
import com.weather.model.*;
import com.weather.service.CacheService;
import com.weather.ui.Display;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Main {
    // Singleton-like instances per il ciclo di vita dell'app
    private static final CacheService cacheService = new CacheService();
    private static final WeatherApiClient apiClient = new WeatherApiClient();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        log.info("Sistema di Monitoraggio Meteo Avanzato avviato.");

        while (true) {
            System.out.print("\nInserisci nome città (o 'exit' per uscire): ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("exit")) break;
            if (city.isEmpty()) continue;

            try {
                // Controllo Cache
                WeatherResponse response = cacheService.get(city);

                if (response == null) {
                    log.info("Dati non in cache o scaduti. Richiesta nuovi dati...");
                    
                    // Geocoding
                    CityCoordinates coords = apiClient.getCoordinates(city);
                    
                    if (coords != null) {
                        // Recupero Meteo Completo
                        response = apiClient.fetchFullWeather(coords.lat(), coords.lon());
                        
                        // Salvataggio in Cache
                        cacheService.put(city, response);
                    } else {
                        log.warn("Errore: Città non trovata.");
                        continue;
                    }
                }

                // Visualizzazione
                Display.printCompleteWeather(city, response);

            } catch (Exception e) {
                log.debug("Errore durante l'elaborazione per {}: {}", city, e.getMessage());
                log.warn("Si è verificato un errore durante il recupero dei dati.");
            }
        }

        log.info("Chiusura applicazione.");
        scanner.close();
    }
}