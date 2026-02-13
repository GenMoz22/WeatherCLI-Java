package com.weather.model;

import java.util.List;

/**
 * Container completo dei dati meteo per una città, 
 * inclusa la logica di validazione della cache.
 */
public record WeatherResponse(
    WeatherData current,
    List<DailyForecast> daily,
    long timestamp
) {
    /**
     * Verifica se i dati sono più vecchi del limite specificato.
     * @param minutes Minuti di validità della cache.
     * @return true se i dati sono scaduti.
     */
    public boolean isExpired(int minutes) {
        return (System.currentTimeMillis() - timestamp) > (minutes * 60 * 1000L);
    }
}