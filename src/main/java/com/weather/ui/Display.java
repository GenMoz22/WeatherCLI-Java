package com.weather.ui;

import com.weather.model.WeatherData;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Display {
    public static void printWeather(String city, WeatherData data) {
        if (data != null) {
            System.out.println("\n--- METEO ---");
            System.out.println("Città: " + city);
            System.out.println("Temperatura attuale: " + data.temperature() + "°C");
            System.out.println("--------------");
        } else {
            log.warn("Errore: Dati meteo non disponibili");
        }
    }
}
