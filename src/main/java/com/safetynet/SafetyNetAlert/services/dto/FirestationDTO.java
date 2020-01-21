package com.safetynet.SafetyNetAlert.services.dto;

import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class FirestationDTO {

    public String getFirestationAddress(Integer stationNumber) {
        String stationAddress;
        if (stationNumber != null) {
            JSONObject jsonData = JSONDAO.getJsonData();
            JSONArray firestations = (JSONArray) jsonData.get((Object) "firestations");
            JSONObject firestation = (JSONObject) firestations.get((int) stationNumber);
            stationAddress = (String) firestation.get((Object) "address");
        } else {
            stationAddress = null;
        }
        return stationAddress;
    }

    public Set<String> getStationAddresses(String stationNumbers) {
        Set<String> stationAddressList = new HashSet<>();
        Map<String, Object> jsonData = JSONDAO.getJsonData();
        ArrayList<Map<String, String>> firestations = (ArrayList)jsonData.get("firestations");
        if (stationNumbers != null) {
            Set<String> stationNumbersSet = new HashSet<>(Arrays.asList(stationNumbers.split(",")));
            for (Map<String, String> firestation : firestations) {
                for (String stationNumber : stationNumbersSet) {
                    if (firestation.get("station").equals(stationNumber)) {
                        stationAddressList.add(firestation.get("address"));
                    }
                }
            }
        } else {
            for (Map<String, String> firestation : firestations) {
                stationAddressList.add(firestation.get("address"));
            }
        }
        return stationAddressList;
    }

    public String getFirestationNumber(String stationAddress) {
        String stationNumber = null;
        if (stationAddress != null) {
            JSONObject jsonData = JSONDAO.getJsonData();
            JSONArray firestationsArray = (JSONArray) jsonData.get((Object) "firestations");
            ArrayList<Map<String, String>> firestations = firestationsArray;
            for (Map<String, String> firestation : firestations) {
                System.out.println(firestation);
                if (firestation.get("address").equals(stationAddress)) {
                    stationNumber = firestation.get("station");

                }
            }
        }
        return stationNumber;
    }
}
