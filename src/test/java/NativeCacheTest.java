import org.junit.Test;
import static org.junit.Assert.*;

public class NativeCacheTest {

    @Test
    public void testPutAndGet() {
        NativeCache<Integer> cache = new NativeCache<>(5, Integer.class);

        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);
        cache.put("key4", 4);
        cache.put("key5", 5);

        assertEquals(1, (int) cache.get("key1"));
        assertEquals(2, (int) cache.get("key2"));
        assertEquals(2, (int) cache.get("key2"));
        assertEquals(3, (int) cache.get("key3"));
        assertEquals(3, (int) cache.get("key3"));
        assertEquals(4, (int) cache.get("key4"));
        assertEquals(4, (int) cache.get("key4"));
        assertEquals(5, (int) cache.get("key5"));
        assertEquals(5, (int) cache.get("key5"));

        assertEquals(3, cache.put("key", 6));
        assertNull(cache.get("key1"));

        cache.put("key7", 7);
        assertNull(cache.get("key6"));
    }

    @Test
    public void testHitsCount() {
        NativeCache<String> cache = new NativeCache<>(5, String.class);

        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        cache.get("key1");
        cache.get("key1");
        cache.get("key2");
        cache.get("key3");
        cache.get("key3");
        cache.get("key3");

        assertEquals(3, cache.hits[0]);
        assertEquals(1, cache.hits[4]);
        assertEquals(2, cache.hits[3]);
    }
}
