package com.safetynet.safetynetalert.service.CRUDservice;


public interface CRUDService<T> {
    T add(T object);
    T set(String key, T object);
    void delete(String key);
}
