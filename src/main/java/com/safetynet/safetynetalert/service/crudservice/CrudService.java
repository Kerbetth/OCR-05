package com.safetynet.safetynetalert.service.crudservice;

/**
 * this interface manage the three data object PErson, MedicalRecord, and Firestation
 * with the same classic methods add/put/delete via the generic denomination Type (T)
 */

public interface CrudService<T> {
    T add(T object);

    T set(String key, T object);

    void delete(String key);
}
