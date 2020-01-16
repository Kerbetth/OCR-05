package com.safetynet.SafetyNetAlert.services;

import com.safetynet.SafetyNetAlert.dto.FirestationDTO;
import com.safetynet.SafetyNetAlert.dto.PersonsDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class JsonOutputServiceFirestation implements JsonOutputService{

    FirestationDTO firestationDTO = new FirestationDTO();
    PersonsDTO jsonDataDTO = new PersonsDTO();
    private Integer stationNumber;


    public JsonOutputServiceFirestation(Integer stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Override
    public String getRequest() {
        Set<JSONObject> personbyStation= new HashSet<>();
        JSONArray persons = jsonDataDTO.getPersons();
        String stationAddress = firestationDTO.getFirestationAddress(stationNumber);
        for (Object personObj : persons) {
            JSONObject person = (JSONObject)personObj;
            if (person.get((Object)"address").equals(stationAddress) || stationAddress == null) {
                JSONObject personInfo = new JSONObject();
                personInfo.put((Object)"firstName", person.get((Object)"firstName"));
                personInfo.put((Object)"lastName", person.get((Object)"lastName"));
                personInfo.put((Object)"address", person.get((Object)"address"));
                personInfo.put((Object)"phone", person.get((Object)"phone"));
                personbyStation.add(personInfo);
            }
        }
        return personbyStation.toString();
    }

}
