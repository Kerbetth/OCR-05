package com.safetynet.SafetyNetAlert.services.dto;

import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.*;

public class PersonsDTO {

    private JSONObject allData;

    public PersonsDTO(){
        this.allData = JSONDAO.getJsonData();
    }


    public JSONArray getPersons() {
        JSONArray persons = (JSONArray) allData.get((Object) "persons");
        return persons;
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
        if (id == -1) {
            personsList.add(personToSet);
        } else {
            personsList.set(id, personToSet);
        }
        updateJSONData(personsList);
    }

    public void removePersonData(Integer id) {
        ArrayList<Object> personsList = getPersons();
        personsList.remove(personsList.get(id));
        updateJSONData(personsList);
    }

    public void updateJSONData(ArrayList<Object> personsList){
        allData.put("persons", personsList);
        JSONDAO.jsonWriter(allData.toString());
    }
}
