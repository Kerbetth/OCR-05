package com.safetynet.SafetyNetAlert.dto;

import com.safetynet.SafetyNetAlert.dao.JSONDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MedicalRecordsDTO {


    public ArrayList<Map<String,String>> getChildrenFirstNameLastNameAndAge() {
        JSONObject jsonData = JSONDAO.getJsonData();
        JSONArray medicalRecords = (JSONArray)jsonData.get((Object)"medicalrecords");
        ArrayList<Map<String,String>>childrenList= new ArrayList<>();
        long longage = 0;
        for (Object medicalRecordOBJ : medicalRecords)
        {
            Map<String,String> medicalRecord = (JSONObject)medicalRecordOBJ;
            try {
                Date birthDate = new SimpleDateFormat("dd/mm/yyyy").parse(medicalRecord.get("birthdate"));
            longage = ((System.currentTimeMillis()/60000 - birthDate.getTime()/60000) / (525560));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Integer age = (int) longage;
            if(age < 18){
                //childData.put((JSONObject) medicalRecord.get("firstName"))
                medicalRecord.remove("medications");
                medicalRecord.remove("allergies");
                medicalRecord.remove("birthdate");
                medicalRecord.put("age", age.toString());
                childrenList.add(medicalRecord);
            }
        }
        return childrenList;
    }
    public JSONArray getMedicalrecords() {
        JSONObject jsonData = JSONDAO.getJsonData();
        JSONArray medicalRecords = (JSONArray)jsonData.get((Object)"medicalrecords");
        return medicalRecords;
    }
}
