package com.safetynet.SafetyNetAlert.beans;

import java.util.*;

public class PersonbyStation
{
    String firstName;
    Integer age;
    String phone;
    Set<String> medication;
    Set<String> allergies;
    
    public PersonbyStation() {
        this.medication = new HashSet<String>();
        this.allergies = new HashSet<String>();
    }
}
