package com.safetynet.safetynetalert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;

import java.io.FileWriter;
import java.io.IOException;



public class WritingCleanJsonData {

    public static void writingCleanJsonDataTest(){
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = "";
        Database newdata = new DataTest().getDatabase();
        try {
            jsonStr = Obj.writeValueAsString(newdata);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter("target/classes/datatest.json")) {
            file.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
