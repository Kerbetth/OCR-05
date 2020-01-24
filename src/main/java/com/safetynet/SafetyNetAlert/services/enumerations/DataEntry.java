package com.safetynet.SafetyNetAlert.services.enumerations;

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
    BIRTHDATE("birthdate"),
    AGE("age"),
    STATION("station"),
    HOUSEMEMBERS("household members"),
    CHILDREN("children"),
    ADULTS("adults"),
    COUNT("counting"),
    PHONEALERT("phone numbers"),
    PERSOBYSTATION("persons by Station"),
    UNKNOWAGE("unknow age");


    private String dataEntry;

    DataEntry(String dataEntry) {
        this.dataEntry = dataEntry;
    }

    public String getString() {
        return dataEntry;
    }
}
