package com.safetynet.SafetyNetAlert.services.firestationservices;




import com.safetynet.SafetyNetAlert.services.dto.FirestationsDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirestationAPIService {

    FirestationsDTO firestationsDTO = new FirestationsDTO();
/*

    public String addFirestationPost(Map<String, String> firestationData) {
        Integer id = -1;
        Map<String, String> firestation = new HashMap<>();
        firestation.put("firstName", firestationData.get("firstName"));
        firestation.put("lastName", firestationData.get("lastName"));
        for (Map.Entry<String, String> value : firestation.entrySet()) {
            if (value.getValue() == null) {
                return "The " + value.getKey() + " value is not specify, operation aborted";
            }
        }
        //firestationsDTO.addfirestationsData(defaultPerson);
        return "Ok";
    }

    public void updatefirestationPut(String firstName, String lastName, Map<String, String> map) {
        Integer id = getIdByName(firstName, lastName);
        Map<String, String> person = (Map) firestationsDTO.getfirestations().get(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
            switch (value.getKey()) {
                case "birthdate":
                    person.put("address", value.getValue());
                    break;
                case "firestations":
                    person.put("city", value.getValue());
                    break;
                case "allergies":
                    person.put("zip", value.getValue());
                    break;
            }
        }
        firestationsDTO.setfirestationData(id, person);
    }

    public void removefirestationDelete(String firstName, String lastName) {
        Integer id = getIdByName(firstName, lastName);
        firestationsDTO.removefirestationData(id);
    }

    public Integer getIdByName(String firstName, String lastName) {
        Integer id = 0;
        for (Map<String, String> currentPerson : (ArrayList<Map>) firestationsDTO.getfirestations()) {
            if (currentPerson.get("firstName") == firstName && currentPerson.get("lastName") == lastName) {
                break;
            } else id++;
        }
        return id;
    }*/
}
