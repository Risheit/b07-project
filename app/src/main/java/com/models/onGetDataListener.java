package com.models;

public interface onGetDataListener<T> {
    void onSuccess(T data);
    void onFailure();
}
