package com.callbacks;

public interface CallBack<T> {

    /**
     * The method run as a callback from an instance of a Caller. Takes in the data currently
     * being held by the Caller instance to allow for the chaining of methods.
     *
     * @param receivedData The data that this callback method receives upon calling.
     */
    T method(T receivedData);
}
