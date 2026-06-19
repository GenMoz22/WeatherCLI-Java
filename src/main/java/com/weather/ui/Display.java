package com.weather.ui;

import com.weather.model.WeatherResponse;
import com.weather.model.DailyForecast;

public class Display {
    public static void printCompleteWeather(String city, WeatherResponse res) {
        System.out.println("\n=== MONITORAGGIO METEO: " + city.toUpperCase() + " ===");
        System.out.printf("Temp: %.1f°C | Vento: %.1f km/h | UV: %.1f | Pioggia Attuale: %d%%\n",
            res.current().temperature(), res.current().windSpeed(), 
            res.current().uvIndex(), res.current().precipProbability());

        System.out.println("\nPREVISIONI 14 GIORNI:");
        System.out.println("+------------+------------+------------+------------+");
        System.out.println("| Data       | Max (°C)   | Min (°C)   | Pioggia %  |");
        System.out.println("+------------+------------+------------+------------+");
        
        for (DailyForecast df : res.daily()) {
            System.out.printf("| %-10s | %-10.1f | %-10.1f | %-10d |\n", 
                df.date(), df.maxTemp(), df.minTemp(), df.precipProbability());
        }
        System.out.println("+------------+------------+------------+------------+");
    }
}