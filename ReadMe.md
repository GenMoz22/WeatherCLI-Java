# WeatherCLI-Java
**WeatherCLI-Java** è uno strumento di monitoraggio meteo da riga di comando. Sfruttando Java e le API di Open-Meteo, offre un'analisi del clima attuale e previsioni a medio termine con un sistema di caching intelligente per ottimizzare le performance.

## Funzioni
* **Monitoraggio Avanzato**: Visualizzazione di temperatura, velocità del vento, indice UV e probabilità di pioggia (attuale e giornaliera).
* **Previsioni a 7 Giorni**: Analisi delle temperature minime e massime per la settimana entrante con output tabellare ASCII.
* **Smart Caching**: Sistema di memorizzazione in-memory (`ConcurrentHashMap`) che riduce le chiamate API ridondanti (validità 30 minuti).
* **Architettura Clean**: Utilizzo di Java **Records** per modelli dati immutabili e **Lombok**
* **Logging Professionale**: Gestione differenziata dei log tramite **SLF4J/Logback** con rotazione automatica e archiviazione.
* **Robustezza**: Gestione delle coordinate internazionali tramite `Locale.US` per prevenire errori di formattazione numerica.

## Installation & Setup

### Prerequisiti

* **Java 17** o superiore.
* **Apache Maven**.

### Step 1: Clonare e Compilare

```bash
git clone https://github.com/GenMoz22/WeatherCLI-Java.git
cd WeatherCLI-Java
mvn clean compile

```

### Step 2: Esecuzione

```bash
mvn exec:java -Dexec.mainClass="com.weather.Main"

```

## Guida all'Uso
L'applicazione è interattiva. Inserisci il nome della città per ottenere il report completo:

```text
Inserisci nome città (o 'exit' per uscire): Milano

=== MONITORAGGIO METEO: MILANO ===
Temp: 12.5°C | Vento: 8.4 km/h | UV: 1.2 | Pioggia Attuale: 15%

PREVISIONI 7 GIORNI:
+------------+------------+------------+------------+
| Data       | Max (°C)   | Min (°C)   | Pioggia %  |
+------------+------------+------------+------------+
| 2026-02-13 | 14.2       | 5.1        | 20         |
| 2026-02-14 | 11.5       | 4.8        | 85         |
+------------+------------+------------+------------+

```

## API Reference
Il progetto utilizza gli endpoint di **Open-Meteo** (nessuna API Key richiesta):

| Servizio | Endpoint | Parametri |
| --- | --- | --- |
| **Geocoding** | `search?name={city}` | `count=1` |
| **Forecast** | `forecast?latitude={lat}&longitude={lon}` | `current=...`, `daily=...` |

## Error Handling & Resilience
* **Network Errors**: Gestione dei timeout e delle mancate connessioni con log di livello `ERROR`.
* **Data Integrity**: Validazione della presenza delle chiavi JSON (`current`, `daily`) prima del parsing.
* **Coordinate Fix**: Utilizzo forzato del punto decimale per garantire la compatibilità internazionale delle URL.

## Logging & Cache
* **Logs**: Salvati in `/logs/weather_app.log`. La console mostra solo `WARN` e superiori, mentre il file logga anche `INFO` e `DEBUG`.
* **Cache**: Se richiedi la stessa città entro 30 minuti, l'app restituisce istantaneamente i dati salvati, evitando traffico di rete inutile.

## Roadmap

* [x] **Dati Avanzati** (UV, Vento, Precipitazioni).
* [x] **Previsioni a 7 giorni** con parsing di array JSON.
* [x] **Sistema di Caching** in-memory.
* [ ] **Esportazione PDF/CSV**: Generazione di report scaricabili.
