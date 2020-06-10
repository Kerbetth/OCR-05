package com.safetynet.safetynetalert.loggerargument;

public class LogArgs {

    public static String getWrongFormatMessage(String argument) {
        return "Error, " + argument + "is not correct, please enter a correct email or phone format";
    }

    public static String getNotEqualSizeMessage() {
        return "Error, the number of persons and of medicalRecords are not the same, please check the JSON file";
    }

    public static String getExistingNameMessage(String name) {
        return "The name \"" + name + "\" allready exist in the database, please precise the name";
    }

    public static String getNoEntryMessage(String name) {
        return "The name or address \"" + name + "\" doesn't correspond to any entry in the database";
    }

    public static String getNoEntryByStationMessage(String station) {
        return "No addresses affilated to the station numbers: \"" + station + "\".";
    }

    public static String getNoFnameOrLNameMessage(String fname, String lname) {
        return "Their is no person with the firstname: \"" + fname + "\" or lastname: \"" + lname + "\"";
    }
}
