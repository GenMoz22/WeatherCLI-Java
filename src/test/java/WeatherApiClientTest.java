import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.weather.api.WeatherApiClient;
import com.weather.model.*;

import java.util.List;

public class WeatherApiClientTest {

    private final WeatherApiClient client = new WeatherApiClient();

    @Test
    @DisplayName("Geocoding Roma - Verifica Coordinate")
    void testGetCoordinatesSuccess() {
        assertDoesNotThrow(() -> {
            CityCoordinates coords = client.getCoordinates("Rome");
            assertNotNull(coords);
            assertTrue(coords.lat() > 41 && coords.lat() < 42);
        });
    }

    @Test
    @DisplayName("Monitoraggio Avanzato - Verifica Dati Correnti e Daily")
    void testFetchFullWeatherSuccess() throws Exception {
        // Coordinate di Milano
        double lat = 45.4642;
        double lon = 9.1899;

        WeatherResponse response = client.fetchFullWeather(lat, lon);

        // Controllo Dati Correnti
        assertNotNull(response);
        assertNotNull(response.current());
        assertTrue(response.current().uvIndex() >= 0);
        assertTrue(response.current().precipProbability() >= 0 && response.current().precipProbability() <= 100);

        // Controllo Previsioni 7 Giorni
        List<DailyForecast> daily = response.daily();
        assertEquals(7, daily.size(), "Dovrebbero esserci 7 giorni di previsione");
        
        // Verifica Probabilità Pioggia
        DailyForecast forecastDay = daily.get(0);
        assertNotNull(forecastDay.date());
        assertTrue(forecastDay.precipProbability() >= 0 && forecastDay.precipProbability() <= 100, 
            "La probabilità di pioggia daily deve essere tra 0 e 100");
    }

    @Test
    @DisplayName("Città Inesistente - Gestione Null")
    void testGetCoordinatesNotFound() throws Exception {
        CityCoordinates coords = client.getCoordinates("NonExistentCity123456");
        assertNull(coords);
    }
}