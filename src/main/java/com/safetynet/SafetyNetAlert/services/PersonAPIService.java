package com.safetynet.SafetyNetAlert.services;




import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonAPIService {

    PersonsDTO personsDTO = new PersonsDTO();

    public String addPersonPost(Map<String, String> personData) {
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
                return "The " + value.getKey() + " value is not specify, operation aborted";
            }
        }
        personsDTO.setPersonsData(id, personData);
        return "Ok";
    }

    public void updatePersonPut(String firstName, String lastName, Map<String, String> map) {
        Integer id = getIdByName(firstName, lastName);
        Map<String, String> person = (Map) personsDTO.getPersons().get(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
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

        personsDTO.setPersonsData(id, person);
    }

    public void removePersonDelete(String firstName, String lastName) {
        Integer id = getIdByName(firstName, lastName);
        personsDTO.removePersonData(id);
    }

    public Integer getIdByName(String firstName, String lastName) {
        Integer id = 0;
        for (Map<String, String> currentPerson : (ArrayList<Map>) personsDTO.getPersons()) {
            if (currentPerson.get("firstName") == firstName && currentPerson.get("lastName") == lastName) {
                break;
            } else id++;
        }
        return id;
    }
}
