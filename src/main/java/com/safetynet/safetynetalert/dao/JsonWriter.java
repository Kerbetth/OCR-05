package com.safetynet.safetynetalert.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {
    public void writer(Database database, String jsonPath){
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = objectMapper.writeValueAsString(database);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter(jsonPath)) {
            file.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
