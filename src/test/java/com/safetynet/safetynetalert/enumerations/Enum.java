package com.safetynet.safetynetalert.enumerations;

public enum Enum {

    FNAME("firstName"),
    LNAME("lastName"),
    ADDRESS("address"),
    CITY("city"),
    ZIP("zip"),
    PHONE("phone"),
    EMAIL("email"),
    BDATE("birthdate"),
    MED("medications"),
    ALLERG("allergies");


    private String dataEntry;

    Enum(String dataEntry) {
        this.dataEntry = dataEntry;
    }

    public String str() {
        return dataEntry;
    }
}
