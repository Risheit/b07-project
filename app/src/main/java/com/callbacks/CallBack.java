package com.callbacks;

public interface CallBack<T> {

    /**
     * Method acts as a wrapper for other non-callback methods. It takes in old data from a caller instance
     * and possibly modifies it. This is done by creating a class that overrides it and implementing
     * Method.
     *
     * @param previousData The data that this callback method receives upon calling.
     */
    T method(T previousData);
}
