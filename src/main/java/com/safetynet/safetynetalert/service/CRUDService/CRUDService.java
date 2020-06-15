package com.safetynet.safetynetalert.service.CRUDService;


public interface CRUDService {
    Object add(Object object);
    Object set(String value, Object object);
    void delete(String value);
}
