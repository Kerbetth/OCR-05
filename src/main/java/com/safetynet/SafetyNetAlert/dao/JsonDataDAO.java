package com.safetynet.SafetyNetAlert.dao;

import org.json.simple.parser.*;
import java.io.*;
import org.json.simple.*;

public class JsonDataDAO
{
    public static JSONObject getJsonData() {
        JSONObject jsonData = new JSONObject();
        try {
            final JSONParser parser = new JSONParser();
            jsonData = (JSONObject)parser.parse((Reader)new FileReader("src/main/resources/data.json"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonData;
    }
    
    public static String getFirestationAddress(final Integer stationNumber) {
        String stationAddress;
        if (stationNumber != null) {
            final JSONObject jsonData = getJsonData();
            final JSONArray firestations = (JSONArray)jsonData.get((Object)"firestations");
            final JSONObject firestation = (JSONObject)firestations.get((int)stationNumber);
            stationAddress = (String)firestation.get((Object)"address");
        }
        else {
            stationAddress = null;
        }
        return stationAddress;
    }
    
    public static JSONArray getPersons() {
        final JSONObject jsonData = getJsonData();
        final JSONArray persons = (JSONArray)jsonData.get((Object)"persons");
        return persons;
    }
    
    public static JSONArray getMedicalrecords() {
        final JSONObject jsonData = getJsonData();
        final JSONArray medicalRecords = (JSONArray)jsonData.get((Object)"medicalrecords");
        return medicalRecords;
    }
}
