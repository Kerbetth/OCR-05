package com.safetynet.SafetyNetAlert.services.dao;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class JSONDAO {

    public static JSONObject getJsonData() {
        JSONObject jsonData = new JSONObject();
        try {
            final JSONParser parser = new JSONParser();
            jsonData = (JSONObject)parser.parse((Reader)new FileReader("src/main/resources/data.json"));
        }
        catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return jsonData;
    }

    public static void jsonWriter(String allData){
        try (FileWriter file = new FileWriter("src/main/resources/data.json")) {
            file.write(allData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
