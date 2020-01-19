package com.safetynet.SafetyNetAlert.dto;

import com.safetynet.SafetyNetAlert.dao.JSONDAO;
import org.json.simple.parser.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.json.simple.*;

public class PersonsDTO
{

    
    public JSONArray getPersons() {
        JSONObject jsonData = JSONDAO.getJsonData();
        JSONArray persons = (JSONArray)jsonData.get((Object)"persons");
        return persons;
    }
    public ArrayList<String> getPersonsData(String dataType) {
        ArrayList<Map<String, String>> personsData = getPersons();
        ArrayList<String> dataList = new ArrayList<>();
        for (Map<String, String> medicalRecordOBJ : personsData) {
            switch (dataType) {
                case "firstName":
                    dataList.add(medicalRecordOBJ.get("firstName"));
                    break;
                case "lastName":
                    dataList.add(medicalRecordOBJ.get("lastName"));
                    break;
                case "address":
                    dataList.add(medicalRecordOBJ.get("address"));
                    break;
                case "city":
                    dataList.add(medicalRecordOBJ.get("city"));
                    break;
                case "zip":
                    dataList.add(medicalRecordOBJ.get("zip"));
                    break;
                case "phone":
                    dataList.add(medicalRecordOBJ.get("phone"));
                    break;
                case "email":
                    dataList.add(medicalRecordOBJ.get("email"));
                    break;
            }
        }
        return dataList;
    }


}
