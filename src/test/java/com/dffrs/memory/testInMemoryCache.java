package com.dffrs.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.dffrs.memory.inMemoryCache.LRUCache;

import org.junit.jupiter.api.Test;

public class testInMemoryCache {

    int size = 10;
    LRUCache<String, String> cache = new LRUCache<String, String>(size);
    int MAX_ELEMENTS_FOR_THIS_TEST = 5;

    @Test
    public void clearCacheTest() {
        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            cache.addElement(String.valueOf(i), String.valueOf(i).concat("element"));
        }

        cache.clearCache();
        assertEquals(cache.getSize(), 0);
    }

    @Test
    public void addFirstElementToCacheTest() {

        cache.addElement("1", "primeiroElement");
        assertEquals(cache.get("1"), "primeiroElement");
        cache.clearCache();
    }

    @Test
    public void addMultipleElementsToCacheTest() {

        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            cache.addElement(String.valueOf(i), String.valueOf(i).concat("element"));
        }

        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            assertEquals(cache.get(String.valueOf(i)), String.valueOf(i).concat("element"));
        }
        cache.clearCache();
    }

    @Test
    public void addMultipleElementsWithCacheHitTest() {
        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST; i++) {
            cache.addElement(String.valueOf(i), String.valueOf(i).concat("element"));
        }

        cache.addElement("0", "element");
        cache.clearCache();
    }

    @Test
    public void removeSpecificElementTest() {
        String removable = "removable";
        cache.addElement(removable, "Element to remove");

        cache.removeElement(removable);

        assertEquals(cache.getSize(), 0);
        cache.clearCache();
    }

    @Test
    public void addMultipleElementsUntilEvictionKicksInTest() {
        for (int i = 0; i != MAX_ELEMENTS_FOR_THIS_TEST * 2; i++) {
            cache.addElement(String.valueOf(i), String.valueOf(i).concat("element"));
        }

        cache.addElement("new", "eviction should kick in");
        assertEquals(cache.get("0"), null);// This element should not be present since is the least recently used.
        cache.clearCache();
    }

    @Test
    public void addMultipleElementsThreadSafetyTest() {
        int numThreads = 5;
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for(int numberOfRuns = 0; numberOfRuns != 10; numberOfRuns++){

            for (int i = 0; i != numThreads; i++) {
                threads.add(new Thread() {
                    @Override
                    public void run() {
                        cache.addElement(String.valueOf(Thread.currentThread().getId()), Thread.currentThread().getName());
                    };
                });
            }
    
            threads.forEach(t -> t.start());
            threads.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
    
            assertEquals(numThreads, cache.getSize());
    
            threads.forEach(t -> assertEquals(t.getName(), cache.get(String.valueOf(t.getId()))));
    
            cache.clearCache();
            threads.clear();
        }
    }
}