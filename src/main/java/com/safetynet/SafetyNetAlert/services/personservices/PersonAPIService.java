package com.safetynet.SafetyNetAlert.services.personservices;


import com.safetynet.SafetyNetAlert.services.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonAPIService {

    PersonsDTO personsDTO = new PersonsDTO();
    private static final Logger logger = LogManager.getLogger("PersonAPIService");

    public String postHTTPMethod(Map<String, String> personData) {
        Integer id = -1;
        Map<String, String> person = new HashMap<>();
        person.put("firstName", personData.get("firstName"));
        person.put("lastName", personData.get("lastName"));
        person.put("address", personData.get("address"));
        person.put("city", personData.get("city"));
        person.put("zip", personData.get("zip"));
        person.put("phone", personData.get("phone"));
        person.put("email", personData.get("email"));
        for (Map.Entry<String, String> value : person.entrySet()) {
            if (value.getValue() == null) {
                logger.error("The " + value.getKey() + " value is not specify, operation aborted");
                return "The " + value.getKey() + " value is not specify, operation aborted";
            }
        }
        personsDTO.addPersonsData(person);
        return "Ok";
    }

    public void putHTTPMethod(String firstName, String lastName, Map<String, String> map) {
        Integer id = personsDTO.getIdByName(firstName, lastName);
        Map<String, String> person = (Map) personsDTO.getPersons().get(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
            if (value.getValue() != "" || value.getValue() != null) {
                switch (value.getKey()) {
                    case "address":
                        person.put("address", value.getValue());
                        break;
                    case "city":
                        person.put("city", value.getValue());
                        break;
                    case "zip":
                        person.put("zip", value.getValue());
                        break;
                    case "phone":
                        person.put("phone", value.getValue());
                        break;
                    case "email":
                        person.put("email", value.getValue());
                        break;
                }
            }
            else logger.error("The " + value.getKey() + " value is not specify, operation aborted");
        }
        personsDTO.setPersonsData(id, person);
    }

    public void deleteHTTPMethod(String firstName, String lastName) {
        Integer id = personsDTO.getIdByName(firstName, lastName);
        personsDTO.removePersonData(id);
    }

}
