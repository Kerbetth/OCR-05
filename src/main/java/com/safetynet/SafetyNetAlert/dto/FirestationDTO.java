package com.safetynet.SafetyNetAlert.dto;

import com.safetynet.SafetyNetAlert.dao.JSONDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FirestationDTO {

    public String getFirestationAddress(Integer stationNumber) {
        String stationAddress;
        if (stationNumber != null) {
            JSONObject jsonData = JSONDAO.getJsonData();
            JSONArray firestations = (JSONArray)jsonData.get((Object)"firestations");
            JSONObject firestation = (JSONObject)firestations.get((int)stationNumber);
            stationAddress = (String)firestation.get((Object)"address");
        }
        else {
            stationAddress = null;
        }
        return stationAddress;
    }
}
