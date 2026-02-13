
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.weather.utils.JsonParser;

public class JsonParserTest {

    @Test
    @DisplayName("Garantisce l'estrazione corretta di un oggetto annidato")
    void testGetObjectSuccess() {
        String json = "{\"results\":[{\"latitude\":10.0}],\"status\":\"OK\"}";
        // Il parser si aspetta la chiave con le virgolette come nel codice reale
        String result = JsonParser.getObject(json, "\"results\"");
        assertTrue(result.contains("\"latitude\":10.0"));
    }

    @Test
    @DisplayName("Verifica il parsing di valori numerici (positivi, negativi, decimali)")
    void testGetDoubleValues() {
        String json = "{\"temp\":-15.5, \"humidity\": 80}";
        assertEquals(-15.5, JsonParser.getDouble(json, "\"temp\""), 0.001);
        assertEquals(80.0, JsonParser.getDouble(json, "\"humidity\""), 0.001);
    }

    @Test
    @DisplayName("Verifica che venga lanciata un'eccezione se la chiave è assente")
    void testKeyNotFound() {
        String json = "{\"a\": 1}";
        assertThrows(IllegalArgumentException.class, () -> {
            JsonParser.getDouble(json, "\"b\"");
        });
    }
}