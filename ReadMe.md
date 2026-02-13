# WeatherCLI-Java

**WeatherCLI-Java** è un'applicazione da riga di comando (CLI) leggera e performante che fornisce dati meteo in tempo reale. Sfruttando la potenza di Java 21 e l'efficienza delle API di Open-Meteo, l'app trasforma nomi di città in coordinate geografiche per restituire dettagli climatici precisi.

## Panoramica

* **Geocoding Dinamico**: Conversione automatica dei nomi delle città in coordinate (Latitudine/Longitudine).
* **Dati Meteo Real-time**: Visualizzazione di temperatura, velocità del vento e umidità.
* **Architettura Moderna**: Utilizzo di Java **Records** per l'immutabilità dei dati e **Lombok**
* **Logging Professionale**: Implementazione di **SLF4J** con rotazione automatica dei log (file vs console).
* **Parsing Custom**: Motore di estrazione JSON leggero integrato senza dipendenze pesanti.

## Installation & Setup

### Prerequisiti

* **Java 17** o superiore.
* **Apache Maven** (per la gestione delle dipendenze e build).

### Step 1: Clonare il repository

```bash
git clone https://github.com/GenMoz22/WeatherCLI-Java.git
cd WeatherCLI-Java

```

### Step 2: Compilazione

Utilizzando Maven per gestire Lombok e le dipendenze:

```bash
mvn clean compile

```

### Step 3: Esecuzione

```bash
mvn exec:java -Dexec.mainClass="com.weather.Main"

```

## Uso
L'applicazione utilizza uno `Scanner` interattivo. Una volta avviata, segui le istruzioni a terminale:

1. **Avvio**: L'app si mette in ascolto dell'input.
2. **Input**: Digita il nome di una città (es. `Milano` o `Tokyo`).
3. **Output**:

```text
Inserisci il nome della città: Milano

--- METEO ---
Città: Milano
Temperatura attuale: 18.5°C
--------------

```

## API Reference
L'app interagisce con l'ecosistema **Open-Meteo**. Non è richiesta alcuna API Key.

| Servizio | Endpoint | Parametri Principali |
| --- | --- | --- |
| **Geocoding** | `search?name={city}` | `count=1`, `language=it` |
| **Forecast** | `forecast?latitude={lat}&longitude={lon}` | `current_weather=true` |

## Error Handling
L'applicazione è progettata per essere resiliente ai comuni scenari di errore:

* **Città non trovata**: Se l'API Geocoding restituisce un array vuoto, l'app logga un `WARN` e informa l'utente in modo gentile.
* **Assenza di Connessione**: Errori di rete vengono intercettati dal blocco `try-catch`, loggati con stack trace completo nel file `logs/app.log` (livello `ERROR`) e comunicati sinteticamente all'utente.
* **Input Non Valido**: Stringhe vuote o caratteri speciali non supportati vengono gestiti tramite `URLEncoder`.

## Logging System
I log sono configurati tramite `logback.xml`:

* **Console**: Mostra solo messaggi `WARN` ed `ERROR`.
* **File (`/logs/weather_app.log`)**: Contiene log dettagliati (livello `DEBUG` e `INFO`) con rotazione automatica al raggiungimento di 10MB.

## Roadmap
* [ ] **Altro oltre alla temperatura!**: Maggiori informazioni.
* [ ] **Previsioni a 7 giorni**: Espansione del parser per gestire array di dati settimanali.


