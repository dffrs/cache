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

    private boolean cacheMissAdd(K key, V element) {
        //TODO: Implement
        return false;
    }

    private boolean cacheHitAdd(K key, V element) {
        //TODO: Implement
        return false;
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
        if(!isEmpty()){
            return this.keyStoringMap.get(key);
        }
        return null;
    }

    @Override
    public boolean addElement(K key, V element) {
        if(key == null || element == null)
            throw new NullPointerException("Error: Null references are not allowed.\n");
        if(this.keyStoringMap.containsKey(key)){
            return cacheHitAdd(key, element);
        }else{
            return cacheMissAdd(key, element);
        }
    }

    @Override
    public void removeElement(K key) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Method to return actual LRUCache size.
     * @return Integer representing number of elements kept by the LRUCache.
     */
    @Override
    public int getSize() {
        return this.initialSize - (this.initialSize - this.keyStoringMap.size());
    }

    @Override
    public void clearCache() {
        // TODO Auto-generated method stub
        
    }
}