
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.weather.model.WeatherResponse;
import java.util.Collections;

public class WeatherModelTest {

    @Test
    @DisplayName("Verifica scadenza Cache (30 minuti)")
    void testCacheExpiration() {
        // Creato ora (non scaduto)
        WeatherResponse fresh = new WeatherResponse(null, Collections.emptyList(), System.currentTimeMillis());
        assertFalse(fresh.isExpired(30));

        // Creato 31 minuti fa (scaduto)
        long oldTimestamp = System.currentTimeMillis() - (31 * 60 * 1000L);
        WeatherResponse stale = new WeatherResponse(null, Collections.emptyList(), oldTimestamp);
        assertTrue(stale.isExpired(30));
    }
}