package com.dffrs.memory.inteface;

/**
 * Interface to accomudate default cache operations.
 */
public interface cache<T> {
    public boolean containsElement(T element);
    public void addElement(T element);
    public void removeElement();
    public int getSize();
}