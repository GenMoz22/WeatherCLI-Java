
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.weather.service.CacheService;
import com.weather.model.WeatherResponse;
import java.util.Collections;

public class CacheServiceTest {

    @Test
    void testCachePutAndGet() {
        CacheService cache = new CacheService();
        WeatherResponse mockResponse = new WeatherResponse(null, Collections.emptyList(), System.currentTimeMillis());
        
        cache.put("Milano", mockResponse);
        
        // Case insensitivity check
        assertNotNull(cache.get("milano"));
        assertNotNull(cache.get("MILANO"));
        assertEquals(mockResponse, cache.get("Milano"));
    }
}