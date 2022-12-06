package com.models;

/**
 * Actions to Take when data of a certain type is received from an asynchronous get method.
 * @param <T> The type of the data received.
 */
public interface onGetDataListener<T> {

    /**
     * The data was received successfully.
     * @param data The data received.
     */
    void onSuccess(T data);

    /**
     * The data was not received.
     */
    void onFailure();
}
