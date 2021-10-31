package com.dffrs.memory;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dffrs.memory.inMemoryCache.LRUCache;

import org.junit.jupiter.api.Test;

public class testInMemoryCache {

    int size = 10;
    LRUCache<String, String> cache = new LRUCache<String, String>(size);
    int MAX_ELEMENTS_FOR_THIS_TEST = 5;

    @Test
    public void clearCache() {
        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            cache.addElement(String.valueOf(i), String.valueOf(i).concat("element"));
        }

        cache.clearCache();
        assertEquals(cache.getSize(), 0);
    }

    @Test
    public void addFirstElementToCache() {

        cache.addElement("1", "primeiroElement");
        assertEquals(cache.get("1"), "primeiroElement");
        cache.clearCache();
    }

    @Test
    public void addMultipleElementsToCache() {

        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            cache.addElement(String.valueOf(i), String.valueOf(i).concat("element"));
        }

        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            assertEquals(cache.get(String.valueOf(i)), String.valueOf(i).concat("element"));
        }
        cache.clearCache();
    }

    @Test
    public void addMultipleElementsWithCacheHit() {
        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            cache.addElement(String.valueOf(i), String.valueOf(i).concat("element"));
        }

        cache.addElement("0", "element");
        cache.clearCache();
    }

    @Test
    public void removeSpecificElement() {
        String removable = "removable";
        cache.addElement(removable, "Element to remove");

        cache.removeElement(removable);

        assertEquals(cache.getSize(), 0);
    }
}