package com.weather.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonParser {

    // Estrae un oggetto JSON a partire da una chiave (es: "current_weather")
    public static String getObject(String json, String key) {
        log.info("Estraggo un oggetto JSON {} partendo dalla chiave {}",json,key);
        int start = json.indexOf(key);
        if (start == -1) {
            log.debug("Chiave {} non trovata!",key);
            throw new IllegalArgumentException("Chiave non trovata: " + key);
        }

        // Vai al primo {
        start = json.indexOf("{", start);
        int braceCount = 0;
        int i = start;

        for (; i < json.length(); i++) {
            if (json.charAt(i) == '{') braceCount++;
            if (json.charAt(i) == '}') braceCount--;
            if (braceCount == 0) break;
        }

        return json.substring(start, i + 1);
    }

    // Estrae un double da un oggetto JSON semplice
    public static double getDouble(String json, String key) {
        log.info("Estraggo un dobule partendo da un oggetto JSON {} con chiave {}",json,key);
        int index = json.indexOf(key);
        if (index == -1) {
            log.debug("Chiave {} non trovata!",key);
            throw new IllegalArgumentException("Chiave non trovata: " + key);
        }

        index += key.length();

        while (index < json.length() &&
              (json.charAt(index) == ' ' || json.charAt(index) == '"' || json.charAt(index) == ':')) {
            index++;
        }

        int end = index;
        while (end < json.length() &&
              (Character.isDigit(json.charAt(end)) || json.charAt(end) == '.' || json.charAt(end) == '-')) {
            end++;
        }

        return Double.parseDouble(json.substring(index, end));
    }
}
