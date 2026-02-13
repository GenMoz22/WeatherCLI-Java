package com.weather.model;
/**
 * Rappresenta dati meteo della città per adesso.
 */
public record WeatherData(
    double temperature, 
    double windSpeed, 
    double uvIndex, 
    int precipProbability
) {}
