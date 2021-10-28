package com.dffrs.memory.inteface;

/**
 * Interface to accomudate default cache operations.
 */
public interface CacheInterface<K, V> {
    public V get(K key);
    public boolean addElement(K key, V element);
    public void removeElement(K key);
    public int getSize();
    public void clearCache();
    public default boolean isEmpty(){
        return getSize() == 0;
    }
}