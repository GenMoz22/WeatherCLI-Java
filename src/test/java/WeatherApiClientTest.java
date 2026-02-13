
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.weather.api.WeatherApiClient;
import com.weather.model.CityCoordinates;
import com.weather.model.WeatherData;

public class WeatherApiClientTest {

    private final WeatherApiClient client = new WeatherApiClient();

    @Test
    @DisplayName("Geocoding Roma")
    void testGetCoordinatesSuccess() {
        assertDoesNotThrow(() -> {
            CityCoordinates coords = client.getCoordinates("Rome");

            assertNotNull(coords, "L'API dovrebbe restituire un risultato per una capitale nota come 'Rome'");

            // Verifichiamo che i valori siano in un range sensato per Roma
            assertTrue(coords.lat() != 0, "La latitudine non dovrebbe essere 0");

        }, "Il metodo getCoordinates ha lanciato un'eccezione inaspettata");
    }

    @Test
    @DisplayName("Città Inesistente")
    void testGetCoordinatesNotFound() throws Exception {
        // Nome casuale per forzare il caso results: null
        CityCoordinates coords = client.getCoordinates("XyzNonEsistente123");
        assertNull(coords, "Dovrebbe restituire null se la città non viene trovata");
    }

    @Test
    @DisplayName("Recupero Temperatura tramite Coordinate")
    void testGetWeatherDataSuccess() throws Exception {
        // Coordinate di Tokyo
        double lat = 35.6895;
        double lon = 139.6917;

        WeatherData data = client.getWeatherData(lat, lon);

        assertNotNull(data);
        // Verifica che la temperatura sia in un range realistico
        assertTrue(data.temperature() > -100 && data.temperature() < 60);
    }
}