package com.callbacks;

import androidx.annotation.NonNull;

public class Caller<T> {
    private final T data;

    public Caller(T data) {
        this.data = data;
    }

    /**
     * Runs the callback method given to it. The internal data is passed to the method. The data
     * is possibly modified and returned in a new Caller instance, allowing for a chaining of then
     * methods.
     *
     * @param callBack The Callback containing the method to be run.
     */
    public Caller<T> then(@NonNull CallBack<T> callBack) {
        T newData = callBack.method(data);
        return new Caller<>(newData);
    }

    /**
     *
     * @return The data held by this caller.
     */
    public T getData() {
        return data;
    }
}
