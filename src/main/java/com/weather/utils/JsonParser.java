package com.weather.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonParser {

    // Estrae un oggetto o un array JSON a partire da una chiave (gestisce sia { } che [ ])
    public static String getObject(String json, String key) {
        log.info("Estraggo un blocco JSON partendo dalla chiave {}", key);
        int start = json.indexOf(key);
        if (start == -1) {
            log.debug("Chiave {} non trovata!", key);
            throw new IllegalArgumentException("Chiave non trovata: " + key);
        }

        // Trova l'inizio del blocco: può essere un oggetto '{' o un array '['
        int startObj = json.indexOf("{", start);
        int startArr = json.indexOf("[", start);
        
        int openPos;
        char openChar, closeChar;
        
        if (startObj != -1 && (startArr == -1 || startObj < startArr)) {
            openPos = startObj;
            openChar = '{';
            closeChar = '}';
        } else if (startArr != -1) {
            openPos = startArr;
            openChar = '[';
            closeChar = ']';
        } else {
            throw new IllegalArgumentException("Nessun blocco JSON trovato dopo la chiave: " + key);
        }

        int count = 0;
        int i = openPos;

        for (; i < json.length(); i++) {
            if (json.charAt(i) == openChar) count++;
            if (json.charAt(i) == closeChar) count--;
            if (count == 0) break;
        }

        return json.substring(openPos, i + 1);
    }

    // Estrae in modo sicuro un double da un frammento JSON, pulendo i caratteri non numerici
    public static double getDouble(String json, String key) {
        log.info("Estraggo un double con chiave {}", key);
        int index = json.indexOf(key);
        if (index == -1) {
            log.debug("Chiave {} non trovata!", key);
            throw new IllegalArgumentException("Chiave non trovata: " + key);
        }

        index += key.length();

        // Salta spazi, virgolette, due punti e virgole per arrivare all'inizio del numero
        while (index < json.length() &&
              (json.charAt(index) == ' ' || json.charAt(index) == '"' || json.charAt(index) == ':' || json.charAt(index) == ',')) {
            index++;
        }

        int end = index;
        // Avanza finché trova caratteri che compongono un valore numerico valido
        while (end < json.length() &&
              (Character.isDigit(json.charAt(end)) || json.charAt(end) == '.' || json.charAt(end) == '-')) {
            end++;
        }

        String numberStr = json.substring(index, end).trim();
        return Double.parseDouble(numberStr);
    }
}