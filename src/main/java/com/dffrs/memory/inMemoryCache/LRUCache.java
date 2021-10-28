package com.dffrs.memory.inMemoryCache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dffrs.memory.inteface.CacheInterface;

public class LRUCache<K,V> implements CacheInterface<K, V>{

    private final int initialSize;
    private Map<K, V> keyStoringMap;
    private List<V> valueStoringList;

    public LRUCache(int size){
        this.initialSize = size;
        this.keyStoringMap = new HashMap<K, V>();
        this.valueStoringList = new LinkedList<V>();
    }

    /**
     * Method to return Value V based on K key given as argument. If the map does not contain any traces of that key
     * it returns null.
     * 
     * @param key K value specified to get the value associated with it.
     * @return V value.
     * @null if K key was not present.
     */
    @Override
    public V get(K key) {
        return this.keyStoringMap.get(key);
    }

    @Override
    public void addElement(K key, V element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeElement(K key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void clearCache() {
        // TODO Auto-generated method stub
        
    }
}