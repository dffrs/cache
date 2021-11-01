package com.dffrs.memory.inMemoryCache;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.dffrs.memory.inteface.CacheInterface;

public final class LRUCache<K, V> implements CacheInterface<K, V> {

    private final int initialSize;
    private Map<K, V> keyStoringMap;
    private Deque<V> valueStoringList;
    private ReentrantLock lock = new ReentrantLock();

    public LRUCache(int size) {
        this.initialSize = size;
        this.keyStoringMap = new HashMap<K, V>();
        this.valueStoringList = new LinkedList<V>();
    }

    /**
     * Method that checks if an entry for a specefied key is present in
     * {@link #keyStoringMap}. Used inside {@link #addElement(Object, Object)}
     * method.
     * 
     * @param key Key used for item searching inside the cache.
     * @return True if present, False otherwise.
     */
    private boolean cacheContains(K key) {
        try {
            lock.lock();

            return this.keyStoringMap.containsKey(key);

        } finally {
            lock.unlock();
        }
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
        try {
            lock.lock();

            this.keyStoringMap.put(key, element);
            this.valueStoringList.addFirst(element);

        } finally {
            lock.unlock();
        }
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
        try {
            lock.lock();

            V temp = this.keyStoringMap.get(key);
            this.valueStoringList.remove(temp);// This is not an O(1) method. Get another solution later.
            this.valueStoringList.addFirst(temp);

        } finally {
            lock.unlock();
        }
    }

    /**
     * Method responsible for removing elements from the cache, using a Least
     * Recently Used (LRU) eviction method.
     * 
     * @throws IllegalStateException When cache is empty.
     */
    private void evictionProtocol() {
        if (isEmpty())
            throw new IllegalStateException("ERROR: Cache is empty. Could not evict any item.\n");

        try {
            lock.lock();

            this.keyStoringMap.values().remove(this.valueStoringList.removeLast());

        } finally {
            lock.unlock();
        }
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

    /**
     * Method responsible for adding elements to the cache. If it is full,
     * {@link #evictionProtocol()} is called before adding a new item. If the cache
     * already contains the new element to insert, it moves it's reference, inside
     * {@link #valueStoringList}, to the first place, meaning it is, now, the latest
     * accessed item. On the other hand, if {@link #keyStoringMap} does NOT contain
     * any key for that element, it adds it to the first position by default (inside
     * {@link #valueStoringList}).
     * 
     * @param key     Key used to reference the new item inside the cache.
     * @param element Element to add.
     * @return True if element is added, False otherwise (for some reason).
     * @throws NullPointerException When either argument is a NULL reference.
     */
    @Override
    public boolean addElement(K key, V element) {
        if (key == null || element == null)
            throw new NullPointerException("Error: Null references are not allowed.\n");

        boolean confirmation = false;
        if (getSize() >= this.initialSize) {
            System.out.println("Cache is full. Evicting oldest element.\n");
            evictionProtocol();
        }

        if (cacheContains(key)) {
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
            try {
                lock.lock();
                return this.valueStoringList.remove(this.keyStoringMap.get(key))
                        && this.keyStoringMap.remove(key, this.keyStoringMap.get(key));
            } finally {
                lock.unlock();
            }
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
        try {
            lock.lock();

            return this.initialSize - (this.initialSize - this.keyStoringMap.size());

        } finally {
            lock.unlock();
        }
    }

    /**
     * Method to remove all items inside this cache.
     */
    @Override
    public void clearCache() {
        try {
            lock.lock();

            this.keyStoringMap.clear();
            this.valueStoringList.clear();

        } finally {
            lock.unlock();
        }
    }
}