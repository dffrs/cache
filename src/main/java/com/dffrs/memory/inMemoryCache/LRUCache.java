package com.dffrs.memory.inMemoryCache;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.dffrs.memory.inteface.CacheInterface;

public final class LRUCache<K, V> implements CacheInterface<K, V> {

    private final int initialSize;
    private Map<K, V> keyStoringMap;
    private Deque<V> valueStoringList;

    public LRUCache(int size) {
        this.initialSize = size;
        this.keyStoringMap = new HashMap<K, V>();
        this.valueStoringList = new LinkedList<V>();
    }

    /**
     * Method to cache an item when a miss search occured. If an item was not
     * present in {@link #keyStoringMap}, it will be added to
     * {@link #valueStoringList} and {@link #keyStoringMap}
     * 
     * @param key     Used to add element to the {@link #keyStoringMap}.
     * @param element To add.
     * @return True if the element was added, False if cache is full.
     */
    private void cacheMissAdd(K key, V element) {
        this.keyStoringMap.put(key, element);
        this.valueStoringList.addFirst(element);
    }

    /**
     * Method used when a cache hit occured. If {@link #keyStoringMap} already has
     * an entry for this key, {@link #valueStoringList} updates this element's
     * reference, passing it to the first position (of {@link #valueStoringList} ->
     * where the latest accessed item is stored).
     * 
     * @param key     Used as reference for {@link #keyStoringMap} entry key.
     * @param element To update.
     * @return True if the element was updated.
     */
    private void cacheHitAdd(K key, V element) {
        V temp = this.keyStoringMap.get(key);
        this.valueStoringList.remove(temp);
        this.valueStoringList.addFirst(temp);
    }

    
    private void evictionProtocol() {
        if (isEmpty())
            throw new IllegalStateException("ERROR: Cache is empty. Could not evict any item.\n");
        this.keyStoringMap.values().remove(this.valueStoringList.removeLast());
    }

    /**
     * Method to return Value V based on K key given as argument. If the map does
     * not contain any traces of that key it returns null.
     * 
     * @param key K value specified to get the value associated with it.
     * @return V value.
     * @null if K key was not present.
     */
    @Override
    public V get(K key) {
        if (!isEmpty()) {
            return this.keyStoringMap.get(key);
        }
        return null;
    }

    @Override
    public boolean addElement(K key, V element) {
        if (key == null || element == null)
            throw new NullPointerException("Error: Null references are not allowed.\n");

        boolean confirmation = false;
        if (getSize() >= this.initialSize) {
            System.out.println("Cache is full. Evicting oldest element.\n");
            evictionProtocol();
        }

        if (this.keyStoringMap.containsKey(key)) {
            cacheHitAdd(key, element);
            confirmation = true;
        } else {
            cacheMissAdd(key, element);
            confirmation = true;
        }
        return confirmation;
    }

    /**
     * Method to remove a specific element, passed as argument.
     * 
     * @param key K value used as reference to remove as specific item.
     * @return True if removed succesfully, False otherwise or LRUCache is empty.
     */
    @Override
    public boolean removeElement(K key) {
        if (!isEmpty()) {
            return this.valueStoringList.remove(this.keyStoringMap.get(key))
                    && this.keyStoringMap.remove(key, this.keyStoringMap.get(key));
        }
        return false;
    }

    /**
     * Method to return actual LRUCache size.
     * 
     * @return Integer representing number of elements kept by the LRUCache.
     */
    @Override
    public int getSize() {
        return this.initialSize - (this.initialSize - this.keyStoringMap.size());
    }

    /**
     * Method to remove all items inside this cache.
     */
    @Override
    public void clearCache() {
        this.keyStoringMap.clear();
        this.valueStoringList.clear();
    }
}