package com.safetynet.SafetyNetAlert.services.dto;

import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MedicalRecordsDTO {

    private JSONObject allData;

    public MedicalRecordsDTO() {
        this.allData = JSONDAO.getJsonData();
    }


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
                    dataList.add(((Object) medicalRecordOBJ.get("medications")).toString());
                    break;
                case "allergies":
                    dataList.add(((Object) medicalRecordOBJ.get("allergies")).toString());
                    break;
                case "age":
                    String birthdate = (medicalRecordOBJ.get("birthdate"));
                    dataList.add(getAge(birthdate));
            }
        }
        return dataList;
    }

    public String getAge(String birthdate) {
        long longage = -1;
        try {
            Date birthDate = new SimpleDateFormat("dd/mm/yyyy").parse(birthdate);
            longage = ((System.currentTimeMillis() / 60000 - birthDate.getTime() / 60000) / (525560));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer age;
        if (longage == -1) {
            return "unknow";
        } else {
            age = (int) longage;
        }
        return age.toString();
    }

    public JSONArray getMedicalrecords() {
        JSONObject jsonData = JSONDAO.getJsonData();
        JSONArray medicalRecords = (JSONArray) jsonData.get((Object) "medicalrecords");
        return medicalRecords;
    }

    public void setMedicalRecordData(Integer id, Map medicalRecordToSet) {
        ArrayList<Object> personsList = getMedicalrecords();
        if (id == -1) {
            personsList.add(medicalRecordToSet);
        } else {
            personsList.set(id, medicalRecordToSet);
        }
        updateJSONData(personsList);
    }

    public void addMedicalRecordsData(Map medicalrecordData) {
        Map defaultPersonData = createdefaultPerson(medicalrecordData.get("firstName").toString(), medicalrecordData.get("lastName").toString());
        ArrayList<Map> personsArray = (ArrayList) allData.get("persons");
        ArrayList<Map> medicalRecordsArray = (ArrayList) allData.get("medicalrecords");
        personsArray.add(defaultPersonData);
        medicalRecordsArray.add(medicalrecordData);
        allData.put("medicalRecords", medicalRecordsArray);
        allData.put("persons", personsArray);
        JSONDAO.jsonWriter(allData.toString());
    }

    public Map<String, String> createdefaultPerson(String firstName, String lastName) {
        Map<String, String> defaultPerson = new HashMap<>();
        defaultPerson.put("firstName", firstName);
        defaultPerson.put("lastName", lastName);
        defaultPerson.put("address", "unknow");
        defaultPerson.put("city", "unknow");
        defaultPerson.put("zip", "unknow");
        defaultPerson.put("phone", "unknow");
        defaultPerson.put("email", "unknow");
        return defaultPerson;
    }

    public void removeMedicalRecordData(Integer id) {
        ArrayList<Object> medicalRecordList = getMedicalrecords();
        medicalRecordList.remove(medicalRecordList.get(id));
        updateJSONData(medicalRecordList);
    }

    public void updateJSONData(ArrayList<Object> personsList) {
        allData.put("medicalrecords", personsList);
        JSONDAO.jsonWriter(allData.toString());
    }

    public Integer getIdByName(String firstName, String lastName) {
        Integer id = 0;
        for (Map<String, String> currentPerson : (ArrayList<Map>) getMedicalrecords()) {
            if (currentPerson.get("firstName").equals(firstName) && currentPerson.get("lastName").equals(lastName)) {
                break;
            } else id++;
        }
        return id;
    }
}

