package com.weather.model;

/**
 * Rappresenta la previsione per un singolo giorno includendo la probabilità di pioggia.
 */
public record DailyForecast(
    String date, 
    double maxTemp, 
    double minTemp,
    int precipProbability
) {}