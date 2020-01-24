package com.safetynet.SafetyNetAlert.services;

import java.util.HashMap;
import java.util.Map;

public interface APIServices {
    public String postMethod(Map<String, String> Data);

    public void putMethod(String firstNameLastName, Map<String, String> map);

    public void deleteMethod(String firstName);
}
