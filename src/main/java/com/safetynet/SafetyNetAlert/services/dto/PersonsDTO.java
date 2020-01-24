package com.safetynet.SafetyNetAlert.services.dto;

import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.*;

public class PersonsDTO {

    private JSONObject allData;

    public PersonsDTO() {
        this.allData = JSONDAO.getJsonData();
    }


    public JSONArray getPersons() {
        JSONArray persons = (JSONArray) allData.get((Object) "persons");
        return persons;
    }

    public Map getData() {
        return allData;
    }

    public ArrayList<String> getPersonsData(String dataType) {
        ArrayList<Map> personsData = getPersons();
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

    public void setPersonsData(Integer id, Map personToSet) {
        ArrayList<Object> personsList = getPersons();
        personsList.set(id, personToSet);
        updateJSONData(personsList);
    }

    public void removePersonData(Integer id) {
        ArrayList<Object> personsList = getPersons();
        personsList.remove(personsList.get(id));
        ArrayList<Object> medicalRecordsArray = (ArrayList) allData.get("medicalrecords");
        medicalRecordsArray.remove(medicalRecordsArray.get(id));
        allData.put("medicalRecords", medicalRecordsArray);
        updateJSONData(personsList);
    }

    public void updateJSONData(ArrayList<Object> personsList) {
        allData.put("persons", personsList);
        JSONDAO.jsonWriter(allData.toString());
    }

    public void addPersonsData(Map personData) {
        Map defaultMedicalData = createdefaultMedicalRecord(personData.get("firstName").toString(), personData.get("lastName").toString());
        ArrayList<Map> personsArray = (ArrayList) allData.get("persons");
        ArrayList<Map> medicalRecordsArray = (ArrayList) allData.get("medicalrecords");
        personsArray.add(personData);
        medicalRecordsArray.add(defaultMedicalData);
        allData.put("medicalRecords", medicalRecordsArray);
        allData.put("persons", personsArray);
        JSONDAO.jsonWriter(allData.toString());
    }

    public Map<String, String> createdefaultMedicalRecord(String firstName, String lastName) {
        Map<String, String> defaultMedicalRecord = new HashMap<>();
        defaultMedicalRecord.put("firstName", firstName);
        defaultMedicalRecord.put("lastName", lastName);
        defaultMedicalRecord.put("birthdate", "unknow");
        defaultMedicalRecord.put("medications", "[]");
        defaultMedicalRecord.put("allergies", "[]");
        return defaultMedicalRecord;
    }

    public Integer getIdByName(String firstName, String lastName) {
        Integer id = 0;
        for (Map<String, String> currentPerson : (ArrayList<Map>) getPersons()) {
            if (currentPerson.get("firstName").equals(firstName) && currentPerson.get("lastName").equals(lastName)) {
                break;
            } else id++;
        }
        return id;
    }
}
