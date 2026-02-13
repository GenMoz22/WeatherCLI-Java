package com.weather.service;

import com.weather.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Slf4j
public class CacheService {
    private final Map<String, WeatherResponse> cache = new ConcurrentHashMap<>();
    private static final int EXPIRATION_MINUTES = 30;

    public WeatherResponse get(String city) {
        WeatherResponse cached = cache.get(city.toLowerCase());
        if (cached != null && !cached.isExpired(EXPIRATION_MINUTES)) {
            log.info("Cache HIT per la città: {}", city);
            return cached;
        }
        return null;
    }

    public void put(String city, WeatherResponse response) {
        cache.put(city.toLowerCase(), response);
    }
}