import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dffrs.memory.inMemoryCache.LRUCache;

import org.junit.jupiter.api.Test;

public class testInMemoryCache {
    
    @Test
    public void addFirstElementToCache(){
        int size = 10;
        LRUCache<String, String> cache = new LRUCache<String, String>(size);

        cache.addElement("1", "primeiroElement");
        assertEquals(cache.get("1"), "primeiroElement");
    }
}
