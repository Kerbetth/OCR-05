package com.safetynet.SafetyNetAlert.enumerations;

public enum DataEntry {

    FNAME("firstName"),
    LNAME("lastName"),
    ADDRESS("address"),
    CITY("city"),
    ZIP("zip"),
    PHONE("phone"),
    EMAIL("email"),
    MEDIC("medications"),
    ALLERGI("allergies"),
    BIRTHDATE("birthdate");


    private String dataEntry;

    DataEntry(String dataEntry) {
        this.dataEntry = dataEntry;
    }

    public String getString() {
        return dataEntry;
    }
}
