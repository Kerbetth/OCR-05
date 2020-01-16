package com.safetynet.SafetyNetAlert.services;

import com.safetynet.SafetyNetAlert.dto.FirestationDTO;
import com.safetynet.SafetyNetAlert.dto.PersonsDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class JsonOutputServicePhoneAlert implements JsonOutputService{

    PersonsDTO jsonDataDAO = new PersonsDTO();
    FirestationDTO firestationDTO = new FirestationDTO();
    private Integer firestation;

    public JsonOutputServicePhoneAlert(Integer firestation){
        this.firestation = firestation;
    }

    @Override
    public String getRequest() {
        String stationAddress = firestationDTO.getFirestationAddress(firestation);
        Set<String> phoneNumbersList = new HashSet<>();
        JSONArray persons = jsonDataDAO.getPersons();
        for (Object personObj : persons) {
            JSONObject person = (JSONObject)personObj;
            if (person.get((Object)"address").equals(stationAddress) || stationAddress == null) {
                phoneNumbersList.add("\"" + (person.get((Object)"phone").toString() + "\""));
            }
        }
        return phoneNumbersList.toString();
    }



}
