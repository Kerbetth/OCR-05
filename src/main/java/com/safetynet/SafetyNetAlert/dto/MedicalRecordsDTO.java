package com.safetynet.SafetyNetAlert.dto;

import com.safetynet.SafetyNetAlert.dao.JSONDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MedicalRecordsDTO {


    public ArrayList<String> getMedicalRecordsData(String dataType) {
        ArrayList<Map<String, String>> medicalRecords = getMedicalrecords();
        ArrayList<String> dataList = new ArrayList<>();
        for (Map<String, String> medicalRecordOBJ : medicalRecords) {
            switch (dataType) {
                case "firstName":
                    dataList.add(medicalRecordOBJ.get("firstName"));
                    break;
                case "lastName":
                    dataList.add(medicalRecordOBJ.get("lastName"));
                    break;
                case "medications":
                    dataList.add(((Object)medicalRecordOBJ.get("medications")).toString());
                    break;
                case "allergies":
                    dataList.add(((Object)medicalRecordOBJ.get("allergies")).toString());
                    break;
                case "age":
                    String birthdate = (medicalRecordOBJ.get("birthdate"));
                    dataList.add(getAge(birthdate));
            }
        }
        return dataList;
    }

    public String getAge(String birthdate) {
        long longage = 0;
        try {
            Date birthDate = new SimpleDateFormat("dd/mm/yyyy").parse(birthdate);
            longage = ((System.currentTimeMillis() / 60000 - birthDate.getTime() / 60000) / (525560));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer age = (int) longage;
        return age.toString();
    }

    public JSONArray getMedicalrecords() {
        JSONObject jsonData = JSONDAO.getJsonData();
        JSONArray medicalRecords = (JSONArray) jsonData.get((Object) "medicalrecords");
        return medicalRecords;
    }
}
