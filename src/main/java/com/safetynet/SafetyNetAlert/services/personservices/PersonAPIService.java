package com.safetynet.SafetyNetAlert.services.personservices;


import com.safetynet.SafetyNetAlert.services.APIServices;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonAPIService implements APIServices {

    public DTO dTOPersons = new DTO(Datatype.PERSO);

    private static final Logger logger = LogManager.getLogger("PersonAPIService");

    public String postMethod(Map<String, String> personData) {
        Integer id = -1;
        Map<String, String> person = new HashMap<>();
        person.put(DataEntry.FNAME.getString(), personData.get(DataEntry.FNAME.getString()));
        person.put(DataEntry.LNAME.getString(), personData.get(DataEntry.LNAME.getString()));
        person.put(DataEntry.ADDRESS.getString(), personData.get(DataEntry.ADDRESS.getString()));
        person.put(DataEntry.CITY.getString(), personData.get(DataEntry.CITY.getString()));
        person.put(DataEntry.ZIP.getString(), personData.get(DataEntry.ZIP.getString()));
        person.put(DataEntry.PHONE.getString(), personData.get(DataEntry.PHONE.getString()));
        person.put(DataEntry.EMAIL.getString(), personData.get(DataEntry.EMAIL.getString()));
        for (Map.Entry<String, String> value : person.entrySet()) {
            if (value.getValue() == null) {
                logger.error("The " + value.getKey() + " value is not specify, operation aborted");
                return "The " + value.getKey() + " value is not specify, operation aborted";
            }
        }

        dTOPersons.addData(person);
        return "Ok";
    }

    public void putMethod(String firstNameLastName, Map<String, String> map) {
        Integer id = dTOPersons.getIdByName(firstNameLastName);
        Map<String, String> person = (Map) dTOPersons.getDataTypeContentwithID(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
            if (value.getValue() != "" && value.getValue() != null) {
                switch (value.getKey()) {
                    case "address":
                        person.put(DataEntry.ADDRESS.getString(), value.getValue());
                        break;
                    case "city":
                        person.put(DataEntry.CITY.getString(), value.getValue());
                        break;
                    case "zip":
                        person.put(DataEntry.ZIP.getString(), value.getValue());
                        break;
                    case "phone":
                        person.put(DataEntry.PHONE.getString(), value.getValue());
                        break;
                    case "email":
                        person.put(DataEntry.EMAIL.getString(), value.getValue());
                        break;
                }
            }
            else logger.error("The " + value.getKey() + " value is not specify, operation aborted");
        }
        dTOPersons.setData(id, person);
    }

    public void deleteMethod(String firstNameLastName) {
        Integer id = dTOPersons.getIdByName(firstNameLastName);
        dTOPersons.removeData(id);
    }

}
